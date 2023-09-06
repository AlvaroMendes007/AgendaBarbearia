package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
	
	public Usuario salvarUsuario(Usuario usuario) {
	    return usuarioRepository.save(usuario);
	}
	
	public String getSenhaCriptografadaPorEmail(String email) {
		return usuarioRepository.getSenhaCriptografadaPorEmail(email);
	}
}
