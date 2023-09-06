package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @PostMapping("/barbeiro")
    public void criarBarbeiro(@RequestBody UsuarioRegisterDTO usuarioDTO) {
        Usuario novoUsuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getSenha());
        usuarioService.criarUsuario(novoUsuario, usuarioDTO.getTipoUsuario());
    }

    @PostMapping
    public void criarUsuario(@RequestBody UsuarioRegisterDTO usuarioDTO) {
    	Usuario novoUsuario = new Usuario(usuarioDTO.getEmail(), usuarioDTO.getSenha());
        usuarioService.criarUsuario(novoUsuario, usuarioDTO.getTipoUsuario());
    }
}
