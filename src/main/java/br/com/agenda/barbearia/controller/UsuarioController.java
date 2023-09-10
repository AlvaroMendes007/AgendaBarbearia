package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.UsuarioRegisterDTO;
import br.com.agenda.barbearia.exception.CampoNaoPreenchidoException;
import br.com.agenda.barbearia.exception.EmailDuplicadoException;
import br.com.agenda.barbearia.exception.SemPermissaoExecutarAcaoException;
import br.com.agenda.barbearia.exception.UsuarioNaoEncontradoException;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.PermissaoService;
import br.com.agenda.barbearia.service.RespostaService;
import br.com.agenda.barbearia.service.UsuarioService;
import br.com.agenda.barbearia.util.StringUtil;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private StringUtil stringUtil;

	@Autowired
	private RespostaService respostaService;

	@PostMapping
	public ResponseEntity<?> criarUsuario(@RequestBody UsuarioRegisterDTO usuarioDTO) {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			verificarCamposPreenchidos(usuarioDTO);
			Usuario novoUsuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getSenha());
			usuarioService.criarUsuario(novoUsuario, usuarioDTO.getTipoUsuario());
			return ResponseEntity.ok(novoUsuario);
		} catch (EmailDuplicadoException e) {
			return respostaService.criarRespostaBadRequest(e.getMessage());
		} catch (SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterarUsuario(@PathVariable Long id, @RequestBody UsuarioRegisterDTO usuarioDTO) {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			Usuario usuario = new Usuario();
			usuario.setId(id);
			usuario.setEmail(usuarioDTO.getEmail());
			usuario.setSenha(usuarioDTO.getSenha());
			usuario = usuarioService.alterarUsuario(usuario);
			return ResponseEntity.ok(usuario);
		} catch (UsuarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch (SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			usuarioService.deletarUsuario(id);
			return respostaService.criarRespostaSucessoRemocao("Usuário removido com sucesso!");
		}
		catch (UsuarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch (SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
	}

	private void verificarCamposPreenchidos(UsuarioRegisterDTO usuarioDTO) {
		if (stringUtil.isEmpty(usuarioDTO.getEmail())) {
			throw new CampoNaoPreenchidoException("E-mail não preenchido!");
		}
		if (stringUtil.isEmpty(usuarioDTO.getSenha())) {
			throw new CampoNaoPreenchidoException("Senha não preenchida!");
		}
	}
}
