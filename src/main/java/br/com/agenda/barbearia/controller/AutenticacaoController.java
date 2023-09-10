package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.UsuarioLoginDTO;
import br.com.agenda.barbearia.dto.UsuarioRegisterDTO;
import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.exception.CampoNaoPreenchidoException;
import br.com.agenda.barbearia.exception.EmailDuplicadoException;
import br.com.agenda.barbearia.exception.LoginInvalidoException;
import br.com.agenda.barbearia.exception.TipoUsuarioNaoEncontradoException;
import br.com.agenda.barbearia.model.Autenticacao;
import br.com.agenda.barbearia.model.Resposta;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.security.TokenService;
import br.com.agenda.barbearia.service.SenhaService;
import br.com.agenda.barbearia.service.UsuarioService;
import br.com.agenda.barbearia.util.StringUtil;

@RestController
@RequestMapping("auth")
public class AutenticacaoController {

	private static final String CAMPOS_OBRIGATORIOS_NAO_PREENCHIDOS = "Os campos obrigatórios não estão preenchidos!";

	@Autowired
	private TokenService tokenService;

	@Autowired
	private SenhaService senhaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private StringUtil stringUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO data) {
		Resposta<Autenticacao> resposta = new Resposta<>();
		try {
			Autenticacao autenticacaoLogin = validarLogin(data);
			return ResponseEntity.ok(autenticacaoLogin);
		} catch (LoginInvalidoException e) {
			resposta.setCodigo(HttpStatus.NOT_FOUND.value());
			resposta.setMensagem(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioRegisterDTO data) {
		Resposta<Autenticacao> resposta = new Resposta<>();
		try {
			TipoUsuarioEnum tipoUsuario = TipoUsuarioEnum.fromValue(data.getTipoUsuarioString());
			validarCadastroUsuario(data, tipoUsuario);
			Usuario usuarioCriado = new Usuario(data.getEmail(), data.getSenha());
			usuarioService.criarUsuario(usuarioCriado, tipoUsuario);

			return ResponseEntity.ok(usuarioCriado);
		} catch (EmailDuplicadoException | CampoNaoPreenchidoException | TipoUsuarioNaoEncontradoException e) {
			resposta.setCodigo(HttpStatus.BAD_REQUEST.value());
			resposta.setMensagem(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resposta);
		}
	}

	private boolean campoObrigatorioNaoPreenchido(UsuarioRegisterDTO usuario) {
		return stringUtil.isEmpty(usuario.getEmail()) || stringUtil.isEmpty(usuario.getSenha())
				|| stringUtil.isEmpty(usuario.getTipoUsuarioString());
	}
	
	private void validarCadastroUsuario(UsuarioRegisterDTO data, TipoUsuarioEnum tipoUsuario) {
		if (campoObrigatorioNaoPreenchido(data)) {
			throw new CampoNaoPreenchidoException(CAMPOS_OBRIGATORIOS_NAO_PREENCHIDOS);
		}
		if (tipoUsuario == null) {
			throw new TipoUsuarioNaoEncontradoException();
		}
		if (usuarioService.verificarEmailDuplicado(data.getEmail())) {
			throw new EmailDuplicadoException();
		}
	}

	private Autenticacao validarLogin(UsuarioLoginDTO usuarioRequisicao) {
		String senhaCriptografadaDoBanco = senhaService.obterSenhaCriptografadaPorEmail(usuarioRequisicao.getEmail());
		Autenticacao autenticacao = new Autenticacao();

		if (!senhaService.verificarSenha(usuarioRequisicao.getSenha(), senhaCriptografadaDoBanco)) {
			throw new LoginInvalidoException();
		}

		Usuario usuario = new Usuario(usuarioRequisicao.getEmail(), usuarioRequisicao.getSenha());
		String token = tokenService.generateToken(usuario);
		autenticacao.setToken(token);

		return autenticacao;
	}

}
