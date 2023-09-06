package br.com.agenda.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.repository.TipoUsuarioRepository;

@Service
public class TipoUsuarioService {
	private final TipoUsuarioRepository tipoUsuarioRepository;
	
    @Autowired
    public TipoUsuarioService(TipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }
	
	public List<TipoUsuario> buscarTipos() {
	    return tipoUsuarioRepository.findAll();
	}
}
