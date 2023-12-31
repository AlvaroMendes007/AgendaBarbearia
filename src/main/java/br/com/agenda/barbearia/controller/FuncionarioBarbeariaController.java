package br.com.agenda.barbearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.agenda.barbearia.dto.FuncionarioBarbeariaDTO;
import br.com.agenda.barbearia.enums.TipoUsuarioEnum;
import br.com.agenda.barbearia.exception.CampoNaoPreenchidoException;
import br.com.agenda.barbearia.exception.EstabelecimentoNaoEncontradoException;
import br.com.agenda.barbearia.exception.FuncionarioNaoEncontradoException;
import br.com.agenda.barbearia.exception.SemPermissaoExecutarAcaoException;
import br.com.agenda.barbearia.exception.TipoUsuarioNaoEncontradoException;
import br.com.agenda.barbearia.exception.UsuarioNaoEncontradoException;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.model.FuncionarioBarbearia;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;
import br.com.agenda.barbearia.service.FuncionarioBarbeariaService;
import br.com.agenda.barbearia.service.PermissaoService;
import br.com.agenda.barbearia.service.RespostaService;
import br.com.agenda.barbearia.service.UsuarioService;
import br.com.agenda.barbearia.util.StringUtil;

@RestController
@RequestMapping("funcionarioBarbearia")
public class FuncionarioBarbeariaController {
	
	ResponseEntity<?> responseEntity;

	@Autowired
	private FuncionarioBarbeariaService funcionarioBarbeariaService;
	
	@Autowired
	private EstabelecimentoBarbeariaService estabelecimentoBarbeariaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private StringUtil stringUtil;
	
	@Autowired
	private RespostaService respostaService;
	
	
	@PostMapping
	public ResponseEntity<?> criarFuncionario(@RequestBody FuncionarioBarbeariaDTO funcionarioDTO) {
		responseEntity = null;
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			validarPreenchimentoDTO(funcionarioDTO);
	        
	        FuncionarioBarbearia funcionarioNovo = setFuncionario(funcionarioDTO);
			setUsuarioFuncionario(funcionarioDTO, funcionarioNovo);
			setEstabelecimentoFuncionario(funcionarioDTO, funcionarioNovo);
			
			funcionarioBarbeariaService.criarFuncionario(funcionarioNovo);
			responseEntity = ResponseEntity.ok(funcionarioNovo);
		} catch(CampoNaoPreenchidoException e) {
			responseEntity = respostaService.criarRespostaBadRequest(e.getMessage());
		} catch(EstabelecimentoNaoEncontradoException | UsuarioNaoEncontradoException e) {
			responseEntity = respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			responseEntity = respostaService.criarRespostaForbidden(e.getMessage());
		}
		
		return responseEntity;
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<?> alterarFuncionario(@PathVariable Long id, @RequestBody FuncionarioBarbeariaDTO funcionarioDTO) {
		responseEntity = null;
		try{
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			FuncionarioBarbearia funcionario = new FuncionarioBarbearia();
			funcionario.setId(id);
			funcionario.setNome(funcionarioDTO.getNome());
			funcionario.setFoto(funcionarioDTO.getFoto());
			
			funcionarioBarbeariaService.alterarFuncionario(funcionario);
			responseEntity =  ResponseEntity.ok(funcionario);		
		} catch(FuncionarioNaoEncontradoException e) {
			responseEntity = respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			responseEntity = respostaService.criarRespostaForbidden(e.getMessage());
		}
		
		return responseEntity;
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarFuncionario(@PathVariable Long id) {
		responseEntity = null;
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			funcionarioBarbeariaService.deletarFuncionarioBarbearia(id);
			responseEntity = respostaService.criarRespostaSucessoRemocao("Funcionário removido com sucesso!");
		} catch(FuncionarioNaoEncontradoException e) {
			responseEntity = respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			responseEntity = respostaService.criarRespostaForbidden(e.getMessage());
		}
		
		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarFuncionarioPorId(@PathVariable Long id) {
		responseEntity = null;
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			FuncionarioBarbearia funcionario = funcionarioBarbeariaService.buscarFuncionarioPorId(id);
			responseEntity = ResponseEntity.ok(funcionario);			
		} catch (FuncionarioNaoEncontradoException e) {
			responseEntity = respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			responseEntity = respostaService.criarRespostaForbidden(e.getMessage());
		}
		
		return responseEntity;
	}
	
	@GetMapping("/tipo")
	public ResponseEntity<?> buscarFuncionarioPorTipo(@RequestParam(name = "tipoUsuario") String tipoUsuarioParam) {
		responseEntity = null;
		try {
			permissaoService.verificarPermissaoAdminOuAdminBarbearia();
			TipoUsuarioEnum tipoUsuario = validarEnum(tipoUsuarioParam);
			List<FuncionarioBarbearia> funcionarios = funcionarioBarbeariaService.buscarFuncionarioPorTipo(tipoUsuario);
			responseEntity = ResponseEntity.ok(funcionarios);
		} catch (FuncionarioNaoEncontradoException | TipoUsuarioNaoEncontradoException e) {
			responseEntity = respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			responseEntity = respostaService.criarRespostaForbidden(e.getMessage());
		}
		
		return responseEntity;
	}

	private TipoUsuarioEnum validarEnum(String tipoUsuarioParam) {
		TipoUsuarioEnum tipoUsuario = TipoUsuarioEnum.fromValue(tipoUsuarioParam);
		if (tipoUsuario == null) {
			throw new TipoUsuarioNaoEncontradoException();
		}
		return tipoUsuario;
	}

	private void setEstabelecimentoFuncionario(FuncionarioBarbeariaDTO funcionarioDTO, FuncionarioBarbearia funcionarioNovo) {
		EstabelecimentoBarbearia estabelecimentoBarbearia = estabelecimentoBarbeariaService.buscarEstabelecimentoPorId(funcionarioDTO.getEstabelecimentoId()); 
		funcionarioNovo.setEstabelecimentoBarbearia(estabelecimentoBarbearia);
	}

	private void setUsuarioFuncionario(FuncionarioBarbeariaDTO funcionarioDTO, FuncionarioBarbearia funcionarioNovo) {
		Usuario usuarioFuncionario = usuarioService.buscarUsuarioPorId(funcionarioDTO.getUsuarioId());
		funcionarioNovo.setUsuario(usuarioFuncionario);
	}

	private FuncionarioBarbearia setFuncionario(FuncionarioBarbeariaDTO funcionarioDTO) {
		FuncionarioBarbearia funcionarioNovo = new FuncionarioBarbearia();
		funcionarioNovo.setFoto(funcionarioDTO.getFoto());
		funcionarioNovo.setNome(funcionarioDTO.getNome());
		return funcionarioNovo;
	}

	private void validarPreenchimentoDTO(FuncionarioBarbeariaDTO funcionarioDTO){
        if (stringUtil.isEmpty(funcionarioDTO.getNome())) {
        	throw new CampoNaoPreenchidoException("Nome não preenchido");
        }
        if (funcionarioDTO.getUsuarioId() == null) {
        	throw new CampoNaoPreenchidoException("Usuario não preenchido");
        }
        if (funcionarioDTO.getEstabelecimentoId() == null) {
        	throw new CampoNaoPreenchidoException("Estabelecimento não preenchido");
        }
	}
}
