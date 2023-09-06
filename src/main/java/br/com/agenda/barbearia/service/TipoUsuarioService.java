package br.com.agenda.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	public TipoUsuario obterTipoUsuarioPorId(int tipoUsuarioId) {
        return tipoUsuarioRepository.findById(Long.valueOf(tipoUsuarioId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de usuário não encontrado com o ID fornecido"));
    }
}
