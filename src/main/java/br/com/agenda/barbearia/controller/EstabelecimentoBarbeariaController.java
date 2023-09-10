package br.com.agenda.barbearia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.EnderecoDTO;
import br.com.agenda.barbearia.dto.EstabelecimentoBarbeariaDTO;
import br.com.agenda.barbearia.exception.CampoNaoPreenchidoException;
import br.com.agenda.barbearia.exception.EstabelecimentoNaoEncontradoException;
import br.com.agenda.barbearia.exception.SemPermissaoExecutarAcaoException;
import br.com.agenda.barbearia.model.Endereco;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;
import br.com.agenda.barbearia.service.PermissaoService;
import br.com.agenda.barbearia.service.RespostaService;
import br.com.agenda.barbearia.util.StringUtil;

@RestController
@RequestMapping("estabelecimentoBarbearia")
public class EstabelecimentoBarbeariaController {

	@Autowired
	private EstabelecimentoBarbeariaService estabelecimentoBarbeariaService;

	@Autowired
	private PermissaoService permissaoService;

	@Autowired
	private RespostaService respostaService;

	@Autowired
	private StringUtil stringUtil;

	@PostMapping
	public ResponseEntity<?> criarEstabelecimento(@RequestBody EstabelecimentoBarbeariaDTO barbeariaDTO) {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			validarCamposPreenchidosBarbearia(barbeariaDTO);
			validarCamposPreenchidosEnderecoBarbearia(barbeariaDTO.getEndereco());
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
			return ResponseEntity.ok(novoEstabelecimento);
		} catch (SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		} catch (CampoNaoPreenchidoException e) {
			return respostaService.criarRespostaBadRequest(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> alterarEstabelecimento(@PathVariable Long id, @RequestBody EstabelecimentoBarbeariaDTO barbeariaDTO) {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
	
			EstabelecimentoBarbearia estabelecimento = new EstabelecimentoBarbearia();
			estabelecimento.setId(id);
			estabelecimento.setNome(barbeariaDTO.getNome());
			estabelecimentoBarbeariaService.alterarEstabelecimentoBarbearia(estabelecimento);
			return ResponseEntity.ok(estabelecimento);
		} catch (SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		} catch (EstabelecimentoNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		}
	}

	@PutMapping("/{id}/endereco")
	public ResponseEntity<?> alterarEnderecoEstabelecimento(@PathVariable Long id, @RequestBody EnderecoDTO enderecoAtualizado) {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			EstabelecimentoBarbearia estabelecimento = estabelecimentoBarbeariaService.buscarEstabelecimentoPorId(id);
			validarCamposPreenchidosEnderecoBarbearia(enderecoAtualizado);

			estabelecimento.getEnderecoBarbearia().setLogradouro(enderecoAtualizado.getLogradouro());
			estabelecimento.getEnderecoBarbearia().setCidade(enderecoAtualizado.getCidade());
			estabelecimento.getEnderecoBarbearia().setBairro(enderecoAtualizado.getBairro());
			estabelecimento.getEnderecoBarbearia().setNumero(enderecoAtualizado.getNumero());
			estabelecimento.getEnderecoBarbearia().setUf(enderecoAtualizado.getUf());
			estabelecimento.getEnderecoBarbearia().setComplemento(enderecoAtualizado.getComplemento());

			estabelecimentoBarbeariaService.alterarEstabelecimentoBarbearia(estabelecimento);
			return ResponseEntity.ok(estabelecimento);
		} catch (SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		} catch (EstabelecimentoNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarEstabelecimento(@PathVariable Long id) throws Exception {
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			estabelecimentoBarbeariaService.deletarEstabelecimentoBarbearia(id);
			return respostaService.criarRespostaSucessoRemocao("Estabelecimento removido com sucesso!");
		} catch(SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		} catch(EstabelecimentoNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		}
	}

	private void validarCamposPreenchidosBarbearia(EstabelecimentoBarbeariaDTO barbeariaDTO) {
		if (stringUtil.isEmpty(barbeariaDTO.getNome())) {
			throw new CampoNaoPreenchidoException("Nome não preenchido");
		}
	}
	
	private void validarCamposPreenchidosEnderecoBarbearia(EnderecoDTO enderecoDTO) {
		if (enderecoDTO == null) {
			throw new CampoNaoPreenchidoException("Endereço não preenchido");
		}
		if (stringUtil.isEmpty(enderecoDTO.getUf())) {
			throw new CampoNaoPreenchidoException("Estado não preenchido");
		}
		if (stringUtil.isEmpty(enderecoDTO.getBairro())) {
			throw new CampoNaoPreenchidoException("Bairro não preenchido");
		}
		if (stringUtil.isEmpty(enderecoDTO.getCidade())) {
			throw new CampoNaoPreenchidoException("Cidade não preenchida");
		}
		if (stringUtil.isEmpty(enderecoDTO.getLogradouro())) {
			throw new CampoNaoPreenchidoException("Logradouro não preenchido");
		}
	}

}
