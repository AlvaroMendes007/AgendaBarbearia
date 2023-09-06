package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private final UsuarioRepository usuarioRepository;
	
	@Autowired
	private final SenhaService senhaService;
	
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, SenhaService senhaService) {
        this.usuarioRepository = usuarioRepository;
        this.senhaService = senhaService;
    }
	
	public Usuario criarUsuario(Usuario usuario, TipoUsuario tipoUsuario) {
        String senhaCriptografada = senhaService.criptografarSenha(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
		usuario.getTipoUsuario().setId(tipoUsuario.getId());
	    return usuarioRepository.save(usuario);
	}
	
	public boolean verificarEmailDuplicado(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }
	
	public String getSenhaCriptografadaPorEmail(String email) {
		return usuarioRepository.getSenhaCriptografadaPorEmail(email);
	}
}
