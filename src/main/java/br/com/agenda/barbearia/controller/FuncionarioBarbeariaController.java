package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.FuncionarioBarbeariaDTO;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.model.FuncionarioBarbearia;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;
import br.com.agenda.barbearia.service.FuncionarioBarbeariaService;
import br.com.agenda.barbearia.service.UsuarioService;

@RestController
@RequestMapping("funcionarioBarbearia")
public class FuncionarioBarbeariaController {

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_ADMIN_BARBEARIA = "ROLE_ADMIN_BARBEARIA";
	
	@Autowired
	private FuncionarioBarbeariaService funcionarioBarbeariaService;
	
	@Autowired
	private EstabelecimentoBarbeariaService estabelecimentoBarbeariaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public void criarFuncionario(@RequestBody FuncionarioBarbeariaDTO funcionarioDTO) throws Exception {
		boolean usuarioTemTipoAdminBarbearia = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN_BARBEARIA.equals(role.getAuthority()));
        boolean usuarioTemTipoAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN.equals(role.getAuthority()));
		
        if (!usuarioTemTipoAdmin && !usuarioTemTipoAdminBarbearia) {
        	throw new Exception("Você não tem permissão para realizar essa ação!");
        }
        
        FuncionarioBarbearia funcionarioNovo = new FuncionarioBarbearia();
		funcionarioNovo.setFoto(funcionarioDTO.getFoto());
		funcionarioNovo.setNome(funcionarioDTO.getNome());
		
		if (funcionarioDTO.getUsuarioId() == null) {
			throw new Exception("Usuario não preenchido");
		}
		Usuario usuarioFuncionario = usuarioService.buscarUsuarioPorId(funcionarioDTO.getUsuarioId());
		funcionarioNovo.setUsuario(usuarioFuncionario);
		
		if (funcionarioDTO.getEstabelecimentoId() == null) {
			throw new Exception("Estabelecimento não preenchido");
		}
		
		EstabelecimentoBarbearia estabelecimentoBarbearia = estabelecimentoBarbeariaService.buscarEstabelecimentoPorId(funcionarioDTO.getEstabelecimentoId()); 
		funcionarioNovo.setEstabelecimentoBarbearia(estabelecimentoBarbearia);
		
		funcionarioBarbeariaService.criarFuncionario(funcionarioNovo);
	}
}
