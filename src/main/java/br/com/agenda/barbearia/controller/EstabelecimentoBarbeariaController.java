package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.EnderecoDTO;
import br.com.agenda.barbearia.dto.EstabelecimentoBarbeariaDTO;
import br.com.agenda.barbearia.model.Endereco;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;
import br.com.agenda.barbearia.util.RoleUtil;

@RestController
@RequestMapping("estabelecimentoBarbearia")
public class EstabelecimentoBarbeariaController {
	
	@Autowired
	private EstabelecimentoBarbeariaService estabelecimentoBarbeariaService;  
	
	@Autowired
	private RoleUtil roleUtil;
	
	@PostMapping
	public void criarEstabelecimento(@RequestBody EstabelecimentoBarbeariaDTO barbeariaDTO) throws Exception {
		boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminBarbeariaRole();
		boolean usuarioTemTipoAdmin = roleUtil.possuiAdminRole();
		
		if (!usuarioTemTipoAdmin && !usuarioTemTipoAdminBarbearia) {
			throw new Exception("Você não tem permissão para realizar essa ação!");
		}
		
		EnderecoDTO enderecoDTO = new EnderecoDTO();
		enderecoDTO = barbeariaDTO.getEndereco();
		Endereco enderecoBarbearia = new Endereco();
		
		enderecoBarbearia.setBairro(enderecoDTO.getBairro());
		enderecoBarbearia.setCidade(enderecoDTO.getCidade());
		enderecoBarbearia.setLogradouro(enderecoDTO.getLogradouro());
		enderecoBarbearia.setNumero(enderecoDTO.getNumero());
		enderecoBarbearia.setUf(enderecoDTO.getUf());
		enderecoBarbearia.setComplemento(enderecoDTO.getComplemento());
		
		EstabelecimentoBarbearia novoEstabelecimento = new EstabelecimentoBarbearia(barbeariaDTO.getNome(), enderecoBarbearia);
		estabelecimentoBarbeariaService.criarEstabelecimentoBarbearia(novoEstabelecimento);
	}
	
	@PutMapping("/{id}")
	public void alterarEstabelecimento(@PathVariable Long id, @RequestBody EstabelecimentoBarbeariaDTO barbeariaDTO) throws Exception {
		boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminBarbeariaRole();
		boolean usuarioTemTipoAdmin = roleUtil.possuiAdminRole();
		
		if (!usuarioTemTipoAdmin && !usuarioTemTipoAdminBarbearia) {
			throw new Exception("Você não tem permissão para realizar essa ação!");
		}
		
		EstabelecimentoBarbearia estabelecimento = new EstabelecimentoBarbearia();
		estabelecimento.setId(id);
		estabelecimento.setNome(barbeariaDTO.getNome());
		estabelecimentoBarbeariaService.alterarEstabelecimentoBarbearia(estabelecimento);
	}
	
	@PutMapping("/{id}/endereco")
    public void alterarEnderecoEstabelecimento(@PathVariable Long id, @RequestBody EnderecoDTO enderecoAtualizado) throws Exception {
        try {
            EstabelecimentoBarbearia estabelecimento = estabelecimentoBarbeariaService.buscarEstabelecimentoPorId(id);
            
            if (estabelecimento == null) {
            	throw new Exception("Estabelecimento não encontrado!");
            }

            estabelecimento.getEnderecoBarbearia().setLogradouro(enderecoAtualizado.getLogradouro());
            estabelecimento.getEnderecoBarbearia().setCidade(enderecoAtualizado.getCidade());
            estabelecimento.getEnderecoBarbearia().setBairro(enderecoAtualizado.getBairro());
            estabelecimento.getEnderecoBarbearia().setNumero(enderecoAtualizado.getNumero());
            estabelecimento.getEnderecoBarbearia().setUf(enderecoAtualizado.getUf());
            estabelecimento.getEnderecoBarbearia().setComplemento(enderecoAtualizado.getComplemento());

            estabelecimentoBarbeariaService.alterarEstabelecimentoBarbearia(estabelecimento);
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar endereco: " + e.getMessage());
        }
    }
	
	@DeleteMapping("/{id}")
	public void deletarEstabelecimento(@PathVariable Long id) throws Exception {
		estabelecimentoBarbeariaService.deletarEstabelecimentoBarbearia(id);
	}
	
}
