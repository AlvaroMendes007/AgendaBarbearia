package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @PostMapping("/barbeiro")
    @PreAuthorize("hasAuthority('ADMIN_BARBEARIA')")
    public Usuario criarBarbeiro(@RequestBody Usuario usuario) {
        Usuario novoUsuario = new Usuario(usuario.getEmail(), usuario.getSenha());
        TipoUsuario tipoUsuario = new TipoUsuario();
        tipoUsuario.setId(TipoUsuarioEnum.BARBEIRO.getKey());
        novoUsuario.setTipoUsuario(tipoUsuario);
        return usuarioService.criarUsuario(novoUsuario, tipoUsuario);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
		TipoUsuario tipoUsuario = new TipoUsuario();
		tipoUsuario.setId(TipoUsuarioEnum.BARBEIRO.getKey());
        return usuarioService.criarUsuario(usuario, tipoUsuario);
    }

}
