package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.UsuarioRegisterDTO;
import br.com.agenda.barbearia.exception.EmailDuplicadoException;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.UsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public ResponseEntity<?> criarUsuario(@RequestBody UsuarioRegisterDTO usuarioDTO) {
		try {
			Usuario novoUsuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getSenha());
			usuarioService.criarUsuario(novoUsuario, usuarioDTO.getTipoUsuario());
	        return ResponseEntity.ok(novoUsuario);
		} catch(EmailDuplicadoException e) {
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o usuário: " + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public void alterarUsuario(@PathVariable Long id, @RequestBody UsuarioRegisterDTO usuarioDTO) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setSenha(usuarioDTO.getSenha());
		usuarioService.alterarUsuario(usuario);
	}

	@DeleteMapping("/{id}")
	public void deletarUsuario(@PathVariable Long id) throws Exception {
		usuarioService.deletarUsuario(id);
	}
}
