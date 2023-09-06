package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.repository.UsuarioRepository;

@Service
public class SenhaService {
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	public SenhaService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	public String criptografarSenha(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }

    public boolean verificarSenha(String senha, String senhaCriptografada) {
        return new BCryptPasswordEncoder().matches(senha, senhaCriptografada);
    }
    
    public String obterSenhaCriptografadaPorEmail(String email) {
        return usuarioRepository.getSenhaCriptografadaPorEmail(email);
    }
}
