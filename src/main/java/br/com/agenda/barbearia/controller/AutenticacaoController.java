package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.UsuarioLoginDTO;
import br.com.agenda.barbearia.dto.UsuarioRegisterDTO;
import br.com.agenda.barbearia.model.Autenticacao;
import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.repository.TipoUsuarioRepository;
import br.com.agenda.barbearia.repository.UsuarioRepository;
import br.com.agenda.barbearia.security.TokenService;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoUsuarioRepository tipoUsuarioRepository;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<Autenticacao> login(@RequestBody UsuarioLoginDTO data) {
		Autenticacao autenticacaoLogin = validarLogin(data);

		if (autenticacaoLogin == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(autenticacaoLogin);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioRegisterDTO data) {
	    if (usuarioRepository.findByEmail(data.getEmail()) != null) {
	        return ResponseEntity.badRequest().build();
	    }

	    if (!camposPreenchidos(data)) {
	        return ResponseEntity.badRequest().build();
	    }

	    String senhaCriptografada = new BCryptPasswordEncoder().encode(data.getSenha());
	    TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(new Long(data.getTipoUsuario().getKey()))
	            .orElseThrow(() -> new RuntimeException("Tipo de usuário não encontrado com o ID fornecido"));

	    Usuario usuarioCriado = new Usuario(data.getEmail(), senhaCriptografada, tipoUsuario);
	    usuarioRepository.save(usuarioCriado);

	    return ResponseEntity.ok().build();
	}

	private boolean camposPreenchidos(UsuarioRegisterDTO usuario) {
		return usuario.getEmail() != null && !usuario.getEmail().isEmpty() && usuario.getSenha() != null && !usuario.getSenha().isEmpty() && usuario.getTipoUsuario() != null;
	}

	private Autenticacao validarLogin(UsuarioLoginDTO usuarioRequisicao) {
		String senhaCriptografadaDoBanco = usuarioRepository
				.getSenhaCriptografadaPorEmail(usuarioRequisicao.getEmail());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Autenticacao autenticacao = new Autenticacao();

		if (!encoder.matches(usuarioRequisicao.getSenha(), senhaCriptografadaDoBanco)) {
			return null;
		}

		Usuario usuario = new Usuario(usuarioRequisicao.getEmail(), usuarioRequisicao.getSenha());
		String token = tokenService.generateToken(usuario);
		autenticacao.setToken(token);

		return autenticacao;
	}

}
