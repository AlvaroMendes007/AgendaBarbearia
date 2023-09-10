package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.model.TipoUsuario;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.repository.UsuarioRepository;
import br.com.agenda.barbearia.util.RoleUtil;

@Service
public class UsuarioService {

	@Autowired
	private final UsuarioRepository usuarioRepository;

	@Autowired
	private final SenhaService senhaService;

	private final RoleUtil roleUtil;
	
	@Autowired
	public UsuarioService(UsuarioRepository usuarioRepository, SenhaService senhaService, RoleUtil roleUtil) {
		this.usuarioRepository = usuarioRepository;
		this.senhaService = senhaService;
		this.roleUtil = roleUtil;
	}

	public void criarUsuario(Usuario usuario, TipoUsuarioEnum tipoUsuarioEnum) {
        boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminRole();
        
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

	public void deletarUsuario(Long id) throws Exception {
		boolean usuarioTemTipoAdmin = roleUtil.possuiAdminRole();
        boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminBarbeariaRole();
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
        boolean tipoUsuarioBarbeiro = TipoUsuarioEnum.BARBEIRO.getValue().equals(usuarioExistente.getTipoUsuario().getTipo());
        
		if (usuarioTemTipoAdmin) {
	        usuarioRepository.delete(usuarioExistente);
	    } else {
			if (usuarioTemTipoAdminBarbearia) {
				if (tipoUsuarioBarbeiro) {
			        usuarioRepository.delete(usuarioExistente);
			    } else {
			        throw new Exception("Sem permissão para excluir este tipo de usuário" + usuarioExistente.getTipoUsuario().getTipo());
			    }
			} else {
			    throw new Exception("Sem permissão para executar esta ação");
			}
		}
	}
	
	public Usuario buscarUsuarioPorId(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElse(null);
	}

	public boolean verificarEmailDuplicado(String email) {
		return usuarioRepository.findByEmail(email) != null;
	}

	public String getSenhaCriptografadaPorEmail(String email) {
		return usuarioRepository.getSenhaCriptografadaPorEmail(email);
	}

}
