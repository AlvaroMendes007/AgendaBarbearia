package br.com.agenda.barbearia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.model.FuncionarioBarbearia;
import br.com.agenda.barbearia.repository.FuncionarioBarbeariaRepository;

@Service
public class FuncionarioBarbeariaService {

	@Autowired
	private final FuncionarioBarbeariaRepository funcionarioBarbeariaRepository;

	@Autowired
	public FuncionarioBarbeariaService(FuncionarioBarbeariaRepository funcionarioBarbeariaRepository) {
		this.funcionarioBarbeariaRepository = funcionarioBarbeariaRepository;
	}

	public void criarFuncionario(FuncionarioBarbearia funcionarioBarbearia) throws Exception {
		validacaoFuncionario(funcionarioBarbearia);        
       	funcionarioBarbeariaRepository.save(funcionarioBarbearia);
	}

	private void validacaoFuncionario(FuncionarioBarbearia funcionarioBarbearia) throws Exception {
		if (funcionarioBarbearia.getEstabelecimentoBarbearia() == null) {
			throw new Exception("Estabelecimento não existe!");
		}
		if (funcionarioBarbearia.getUsuario() == null) {
			throw new Exception("Usuário não existe!");
		}
	}
}
