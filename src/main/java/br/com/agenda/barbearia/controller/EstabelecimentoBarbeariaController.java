package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.EnderecoDTO;
import br.com.agenda.barbearia.dto.EstabelecimentoBarbeariaDTO;
import br.com.agenda.barbearia.model.Endereco;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;

@RestController
@RequestMapping("estabelecimentoBarbearia")
public class EstabelecimentoBarbeariaController {
	
	@Autowired
	private EstabelecimentoBarbeariaService estabelecimentoBarbeariaService;  
	
	@PostMapping
	public void criarEstabelecimento(@RequestBody EstabelecimentoBarbeariaDTO barbeariaDTO) throws Exception {
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
}
