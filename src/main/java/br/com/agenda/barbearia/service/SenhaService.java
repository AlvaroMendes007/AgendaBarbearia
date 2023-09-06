package br.com.agenda.barbearia.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {
	public String criptografarSenha(String senha) {
        return new BCryptPasswordEncoder().encode(senha);
    }

    public boolean verificarSenha(String senhaPlana, String senhaCriptografada) {
        return new BCryptPasswordEncoder().matches(senhaPlana, senhaCriptografada);
    }
}
