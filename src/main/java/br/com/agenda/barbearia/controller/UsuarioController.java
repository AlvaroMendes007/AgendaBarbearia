package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.UsuarioRegisterDTO;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public void criarUsuario(@RequestBody UsuarioRegisterDTO usuarioDTO) {
		Usuario novoUsuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getSenha());
		usuarioService.criarUsuario(novoUsuario, usuarioDTO.getTipoUsuario());
	}

	@PutMapping("/{id}")
	public void alterarUsuario(@PathVariable Long id, @RequestBody UsuarioRegisterDTO usuarioDTO) throws Exception {
		Usuario usuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getSenha());
		usuario.setId(id);
		usuarioService.alterarUsuario(usuario);
	}

	@DeleteMapping("/{id}")
	public void deletarUsuario(@PathVariable Long id) throws Exception {
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuarioService.deletarUsuario(usuario);
	}
}
