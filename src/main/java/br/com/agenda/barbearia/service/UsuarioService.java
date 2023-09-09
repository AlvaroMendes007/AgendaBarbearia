package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_ADMIN_BARBEARIA = "ROLE_ADMIN_BARBEARIA";

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
        boolean usuarioTemTipoAdminBarbearia = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN_BARBEARIA.equals(role.getAuthority()));
        
		if (!verificarEmailDuplicado(usuario.getEmail())) {
			TipoUsuario tipoUsuario = new TipoUsuario();
			if (usuarioTemTipoAdminBarbearia) {
				tipoUsuario.setTipo(TipoUsuarioEnum.BARBEIRO.getValue());
			}
			String senhaCriptografada = senhaService.criptografarSenha(usuario.getSenha());
			usuario.setSenha(senhaCriptografada);
			tipoUsuario.setId(tipoUsuarioEnum.getKey());
			usuario.setTipoUsuario(tipoUsuario);
			usuarioRepository.save(usuario);
		}
	}

	public void alterarUsuario(Usuario usuario) throws Exception {
		Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
				.orElseThrow(() -> new Exception("Usuário não encontrado"));

		String senhaCriptografada = senhaService.criptografarSenha(usuario.getSenha());
		usuarioExistente.setEmail(usuario.getEmail());
		usuarioExistente.setSenha(senhaCriptografada);
		
		usuarioRepository.save(usuarioExistente);
	}

	public void deletarUsuario(Usuario usuario) throws Exception {
		boolean usuarioTemTipoAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN.equals(role.getAuthority()));
        boolean usuarioTemTipoAdminBarbearia = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN_BARBEARIA.equals(role.getAuthority()));
        
		if (usuarioTemTipoAdmin) {
	        usuarioRepository.delete(usuario);
	    } else {
			if (usuarioTemTipoAdminBarbearia) {
			    Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
			            .orElseThrow(() -> new Exception("Usuário não encontrado"));

			    if (TipoUsuarioEnum.BARBEIRO.getValue().equals(usuarioExistente.getTipoUsuario().getTipo())) {
			        usuarioRepository.delete(usuarioExistente);
			    } else {
			        throw new Exception("Sem permissão para excluir este tipo de usuário" + usuarioExistente.getTipoUsuario().getTipo());
			    }
			} else {
			    throw new Exception("Sem permissão para executar esta ação");
			}
		}
	}

	public boolean verificarEmailDuplicado(String email) {
		return usuarioRepository.findByEmail(email) != null;
	}

	public String getSenhaCriptografadaPorEmail(String email) {
		return usuarioRepository.getSenhaCriptografadaPorEmail(email);
	}

}
