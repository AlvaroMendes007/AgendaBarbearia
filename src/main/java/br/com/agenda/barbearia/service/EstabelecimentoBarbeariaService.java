package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.Endereco;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.repository.EstabelecimentoBarbeariaRepository;

@Service
public class EstabelecimentoBarbeariaService {

	private static final String ROLE_ADMIN_BARBEARIA = "ROLE_ADMIN_BARBEARIA";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private EstabelecimentoBarbeariaRepository estabelecimentoBarbeariaRepository;

	public void criarEstabelecimentoBarbearia(EstabelecimentoBarbearia barbearia) throws Exception {
		boolean usuarioTemTipoAdminBarbearia = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN_BARBEARIA.equals(role.getAuthority()));
		boolean usuarioTemTipoAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(role -> ROLE_ADMIN.equals(role.getAuthority()));

		if (usuarioTemTipoAdmin || usuarioTemTipoAdminBarbearia) {
			Endereco endereco = barbearia.getEnderecoBarbearia();
			endereco = enderecoService.criarEndereco(endereco);
			barbearia.setEnderecoBarbearia(endereco);

			estabelecimentoBarbeariaRepository.save(barbearia);
		} else {
			throw new Exception("Você não tem permissão para realizar essa ação!");
		}
	}
	
	public EstabelecimentoBarbearia buscarEstabelecimentoPorId(Long id) {
		return estabelecimentoBarbeariaRepository.findById(id).orElse(null); 
	}
}
