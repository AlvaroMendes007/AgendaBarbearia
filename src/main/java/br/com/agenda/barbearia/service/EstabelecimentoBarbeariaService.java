package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.Endereco;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.repository.EstabelecimentoBarbeariaRepository;

@Service
public class EstabelecimentoBarbeariaService {

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private EstabelecimentoBarbeariaRepository estabelecimentoBarbeariaRepository;

	public void criarEstabelecimentoBarbearia(EstabelecimentoBarbearia barbearia) throws Exception {
		Endereco endereco = barbearia.getEnderecoBarbearia();
		endereco = enderecoService.criarEndereco(endereco);
		barbearia.setEnderecoBarbearia(endereco);

		estabelecimentoBarbeariaRepository.save(barbearia);
	}

	public void alterarEstabelecimentoBarbearia(EstabelecimentoBarbearia barbearia) throws Exception {
		EstabelecimentoBarbearia barbeariaExistente = estabelecimentoBarbeariaRepository.findById(barbearia.getId())
				.orElseThrow(() -> new Exception("Estabelecimento não encontrado"));

		barbeariaExistente.setNome(barbearia.getNome());
		barbeariaExistente.setEnderecoBarbearia(barbearia.getEnderecoBarbearia());
		
		estabelecimentoBarbeariaRepository.save(barbeariaExistente);
	}

	public void deletarEstabelecimentoBarbearia(Long id) throws Exception {
		EstabelecimentoBarbearia barbeariaExistente = estabelecimentoBarbeariaRepository.findById(id)
				.orElseThrow(() -> new Exception("Estabelecimento não encontrado"));
		
		estabelecimentoBarbeariaRepository.delete(barbeariaExistente);
	}
	
	public EstabelecimentoBarbearia buscarEstabelecimentoPorId(Long id) {
		return estabelecimentoBarbeariaRepository.findById(id).orElse(null);
	}
};