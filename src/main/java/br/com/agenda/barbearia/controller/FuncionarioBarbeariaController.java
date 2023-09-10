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
import br.com.agenda.barbearia.exception.UsuarioNaoEncontradoException;
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.model.FuncionarioBarbearia;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;
import br.com.agenda.barbearia.service.FuncionarioBarbeariaService;
import br.com.agenda.barbearia.service.RespostaService;
import br.com.agenda.barbearia.service.UsuarioService;
import br.com.agenda.barbearia.util.RoleUtil;
import br.com.agenda.barbearia.util.StringUtil;

@RestController
@RequestMapping("funcionarioBarbearia")
public class FuncionarioBarbeariaController {

	@Autowired
	private FuncionarioBarbeariaService funcionarioBarbeariaService;
	
	@Autowired
	private EstabelecimentoBarbeariaService estabelecimentoBarbeariaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RoleUtil roleUtil;
	
	@Autowired
	private StringUtil stringUtil;
	
	@Autowired
	private RespostaService respostaService;
	
	@PostMapping
	public ResponseEntity<?> criarFuncionario(@RequestBody FuncionarioBarbeariaDTO funcionarioDTO) {
		try {
			validarPermissao();
			validarPreenchimentoDTO(funcionarioDTO);
	        
	        FuncionarioBarbearia funcionarioNovo = setFuncionario(funcionarioDTO);
			setUsuarioFuncionario(funcionarioDTO, funcionarioNovo);
			setEstabelecimentoFuncionario(funcionarioDTO, funcionarioNovo);
			
			funcionarioBarbeariaService.criarFuncionario(funcionarioNovo);
			return ResponseEntity.ok(funcionarioNovo);
		} catch(CampoNaoPreenchidoException e) {
			return respostaService.criarRespostaBadRequest(e.getMessage());
		} catch(EstabelecimentoNaoEncontradoException | UsuarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
	}
	
	@PutMapping("/{id}") 
	public ResponseEntity<?> alterarFuncionario(@PathVariable Long id, @RequestBody FuncionarioBarbeariaDTO funcionarioDTO) {
		try{
			validarPermissao();
			FuncionarioBarbearia funcionario = new FuncionarioBarbearia();
			funcionario.setId(id);
			funcionario.setNome(funcionarioDTO.getNome());
			funcionario.setFoto(funcionarioDTO.getFoto());
			
			funcionarioBarbeariaService.alterarFuncionario(funcionario);
			return ResponseEntity.ok(funcionario);		
		} catch(FuncionarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarFuncionario(@PathVariable Long id) {
		try {
			validarPermissao();
			funcionarioBarbeariaService.deletarFuncionarioBarbearia(id);
			return respostaService.criarRespostaRemocaoFuncionario("Funcionário removido com sucesso!");
		} catch(FuncionarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarFuncionarioPorId(@PathVariable Long id) {
		try {
			validarPermissao();
			FuncionarioBarbearia funcionario = funcionarioBarbeariaService.buscarFuncionarioPorId(id);
			return ResponseEntity.ok(funcionario);			
		} catch (FuncionarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
	}
	
	@GetMapping("/tipo")
	public ResponseEntity<?> buscarFuncionarioPorTipo(@RequestParam(name = "tipoUsuario") TipoUsuarioEnum tipoUsuario) {
		try {
			validarPermissao();
			List<FuncionarioBarbearia> funcionarios = funcionarioBarbeariaService.buscarFuncionarioPorTipo(tipoUsuario);
			return ResponseEntity.ok(funcionarios);
		} catch (FuncionarioNaoEncontradoException e) {
			return respostaService.criarRespostaNotFound(e.getMessage());
		} catch(SemPermissaoExecutarAcaoException e) {
			return respostaService.criarRespostaForbidden(e.getMessage());
		}
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
	
	private void validarPermissao(){
		boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminBarbeariaRole();
        boolean usuarioTemTipoAdmin = roleUtil.possuiAdminRole();
		
        if (!usuarioTemTipoAdmin && !usuarioTemTipoAdminBarbearia) {
        	throw new SemPermissaoExecutarAcaoException();
        }
	}
}
