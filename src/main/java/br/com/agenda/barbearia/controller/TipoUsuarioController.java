package br.com.agenda.barbearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.service.TipoUsuarioService;

@RestController
@RequestMapping("/tiposUsuario")
public class TipoUsuarioController {
	private final TipoUsuarioService tipoUsuarioService;

    @Autowired
    public TipoUsuarioController(TipoUsuarioService tipoUsuarioService) {
        this.tipoUsuarioService = tipoUsuarioService;
    }

    @GetMapping
    public List<TipoUsuario> buscarTiposUsuario() {
        return tipoUsuarioService.buscarTipos();
    }
}
