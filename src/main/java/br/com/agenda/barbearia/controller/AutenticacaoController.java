package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.UsuarioLoginDTO;
import br.com.agenda.barbearia.dto.UsuarioRegisterDTO;
import br.com.agenda.barbearia.model.Autenticacao;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.security.TokenService;
import br.com.agenda.barbearia.service.SenhaService;
import br.com.agenda.barbearia.service.UsuarioService;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private SenhaService senhaService;
	
	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<Autenticacao> login(@RequestBody UsuarioLoginDTO data) {
		Autenticacao autenticacaoLogin = validarLogin(data);

		if (autenticacaoLogin == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(autenticacaoLogin);
	}

	@PostMapping("/register")
	public ResponseEntity<BodyBuilder> register(@RequestBody UsuarioRegisterDTO data) {
	    if (usuarioService.verificarEmailDuplicado(data.getEmail())) {
	        return ResponseEntity.badRequest().build();
	    }

	    if (!camposPreenchidos(data)) {
	        return ResponseEntity.badRequest().build();
	    }

	    Usuario usuarioCriado = new Usuario(data.getEmail(), data.getSenha());
	    usuarioService.criarUsuario(usuarioCriado, data.getTipoUsuario());

	    return ResponseEntity.ok().build();
	}

	private boolean camposPreenchidos(UsuarioRegisterDTO usuario) {
		return usuario.getEmail() != null && !usuario.getEmail().isEmpty() && usuario.getSenha() != null && !usuario.getSenha().isEmpty() && usuario.getTipoUsuario() != null;
	}

	private Autenticacao validarLogin(UsuarioLoginDTO usuarioRequisicao) {
		String senhaCriptografadaDoBanco = senhaService.obterSenhaCriptografadaPorEmail(usuarioRequisicao.getEmail());
		Autenticacao autenticacao = new Autenticacao();

		if (!senhaService.verificarSenha(usuarioRequisicao.getSenha(), senhaCriptografadaDoBanco)){
			return null;
		}

		Usuario usuario = new Usuario(usuarioRequisicao.getEmail(), usuarioRequisicao.getSenha());
		String token = tokenService.generateToken(usuario);
		autenticacao.setToken(token);

		return autenticacao;
	}

}
