package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
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
	
	public void criarUsuario(Usuario usuario, TipoUsuarioEnum tipoUsuarioEnum) {
        if (!verificarEmailDuplicado(usuario.getEmail())) {
        	String senhaCriptografada = senhaService.criptografarSenha(usuario.getSenha());
            usuario.setSenha(senhaCriptografada);
            TipoUsuario tipoUsuario = new TipoUsuario();
            tipoUsuario.setId(tipoUsuarioEnum.getKey());
            usuario.setTipoUsuario(tipoUsuario);	
            usuarioRepository.save(usuario);
        }
	}
	
	public boolean verificarEmailDuplicado(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }
	
	public String getSenhaCriptografadaPorEmail(String email) {
		return usuarioRepository.getSenhaCriptografadaPorEmail(email);
	}
}
