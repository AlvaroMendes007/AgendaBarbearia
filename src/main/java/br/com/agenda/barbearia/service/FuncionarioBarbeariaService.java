package br.com.agenda.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.exception.EstabelecimentoNaoEncontradoException;
import br.com.agenda.barbearia.exception.FuncionarioNaoEncontradoException;
import br.com.agenda.barbearia.exception.UsuarioNaoEncontradoException;
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

	public void criarFuncionario(FuncionarioBarbearia funcionarioBarbearia)  {
		validacaoFuncionario(funcionarioBarbearia);        
       	funcionarioBarbeariaRepository.save(funcionarioBarbearia);
	}
	
	public void alterarFuncionario(FuncionarioBarbearia funcionarioBarbearia){
		FuncionarioBarbearia funcionarioExistente = funcionarioBarbeariaRepository.findById(funcionarioBarbearia.getId())
				.orElseThrow(() -> new FuncionarioNaoEncontradoException());
		
		funcionarioExistente.setNome(funcionarioBarbearia.getNome());
		funcionarioExistente.setFoto(funcionarioBarbearia.getFoto());
		
       	funcionarioBarbeariaRepository.save(funcionarioExistente);
	}
	
	public void deletarFuncionarioBarbearia(Long id) {
		FuncionarioBarbearia funcionarioExistente = funcionarioBarbeariaRepository.findById(id)
				.orElseThrow(() -> new FuncionarioNaoEncontradoException());
		
		funcionarioBarbeariaRepository.delete(funcionarioExistente);
	}
	
	private void validacaoFuncionario(FuncionarioBarbearia funcionarioBarbearia) {
		if (funcionarioBarbearia.getEstabelecimentoBarbearia() == null) {
			throw new EstabelecimentoNaoEncontradoException();
		}
		if (funcionarioBarbearia.getUsuario() == null) {
			throw new UsuarioNaoEncontradoException();
		}
	}

	public FuncionarioBarbearia buscarFuncionarioPorId(Long id) {
		return funcionarioBarbeariaRepository.findById(id)
				.orElseThrow(() -> new FuncionarioNaoEncontradoException());
	}
	
	public List<FuncionarioBarbearia> buscarFuncionarioPorTipo(TipoUsuarioEnum tipoUsuario) {
	  List<FuncionarioBarbearia> funcionarios = funcionarioBarbeariaRepository.findByTipo(tipoUsuario.getKey());
	    if (funcionarios.isEmpty()) {
	        throw new FuncionarioNaoEncontradoException();
	    }
	
	    return funcionarios;
	}
}
