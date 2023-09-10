package br.com.agenda.barbearia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
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
	
	public void alterarFuncionario(FuncionarioBarbearia funcionarioBarbearia) throws Exception {
		FuncionarioBarbearia funcionarioExistente = funcionarioBarbeariaRepository.findById(funcionarioBarbearia.getId())
				.orElseThrow(() -> new Exception("Funcionário não encontrado"));
		
		funcionarioExistente.setNome(funcionarioBarbearia.getNome());
		funcionarioExistente.setFoto(funcionarioBarbearia.getFoto());
		funcionarioExistente.setEstabelecimentoBarbearia(funcionarioBarbearia.getEstabelecimentoBarbearia());
		funcionarioExistente.setUsuario(funcionarioBarbearia.getUsuario());
		
       	funcionarioBarbeariaRepository.save(funcionarioExistente);
	}
	
	public void deletarFuncionarioBarbearia(Long id) throws Exception {
		FuncionarioBarbearia funcionarioExistente = funcionarioBarbeariaRepository.findById(id)
				.orElseThrow(() -> new Exception("Funcionário não encontrado"));
		
		funcionarioBarbeariaRepository.delete(funcionarioExistente);
	}
	
	private void validacaoFuncionario(FuncionarioBarbearia funcionarioBarbearia) throws Exception {
		if (funcionarioBarbearia.getEstabelecimentoBarbearia() == null) {
			throw new Exception("Estabelecimento não existe!");
		}
		if (funcionarioBarbearia.getUsuario() == null) {
			throw new Exception("Usuário não existe!");
		}
	}

	public FuncionarioBarbearia buscarFuncionarioPorId(Long id) {
		return funcionarioBarbeariaRepository.findById(id).orElse(null);
	}
	
	public List<FuncionarioBarbearia> buscarFuncionarioPorTipo(TipoUsuarioEnum tipoUsuario) {
		return funcionarioBarbeariaRepository.findByTipo(tipoUsuario.getKey());
	}
}
