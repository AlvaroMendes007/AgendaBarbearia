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
import br.com.agenda.barbearia.model.EstabelecimentoBarbearia;
import br.com.agenda.barbearia.model.FuncionarioBarbearia;
import br.com.agenda.barbearia.model.Usuario;
import br.com.agenda.barbearia.service.EstabelecimentoBarbeariaService;
import br.com.agenda.barbearia.service.FuncionarioBarbeariaService;
import br.com.agenda.barbearia.service.UsuarioService;
import br.com.agenda.barbearia.util.RoleUtil;

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
	
	@PostMapping
	public void criarFuncionario(@RequestBody FuncionarioBarbeariaDTO funcionarioDTO) throws Exception {
		validacaoPreenchimentoDTO(funcionarioDTO);
        
        FuncionarioBarbearia funcionarioNovo = setFuncionario(funcionarioDTO);
		setUsuarioFuncionario(funcionarioDTO, funcionarioNovo);
		setEstabelecimentoFuncionario(funcionarioDTO, funcionarioNovo);
		
		funcionarioBarbeariaService.criarFuncionario(funcionarioNovo);
	}
	
	@PutMapping("/{id}") 
	public void alterarFuncionario(@PathVariable Long id, @RequestBody FuncionarioBarbeariaDTO funcionarioDTO) throws Exception {
		FuncionarioBarbearia funcionario = new FuncionarioBarbearia();
		
		funcionario.setId(id);
		funcionario.setNome(funcionarioDTO.getNome());
		funcionario.setFoto(funcionarioDTO.getFoto());
		
		funcionarioBarbeariaService.alterarFuncionario(funcionario);
	}
	
	@DeleteMapping("/{id}")
	public void deletarFuncionario(@PathVariable Long id) throws Exception {
		funcionarioBarbeariaService.deletarFuncionarioBarbearia(id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FuncionarioBarbearia> buscarFuncionarioPorId(@PathVariable Long id) {
		FuncionarioBarbearia funcionario = funcionarioBarbeariaService.buscarFuncionarioPorId(id);
	    
	    if (funcionario != null) {
	        return ResponseEntity.ok(funcionario);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/tipo")
	public ResponseEntity<List<FuncionarioBarbearia>> buscarFuncionarioPorTipo(@RequestParam(name = "tipoUsuario") TipoUsuarioEnum tipoUsuario) {
		List<FuncionarioBarbearia> funcionarios = funcionarioBarbeariaService.buscarFuncionarioPorTipo(tipoUsuario);
	    
	    if (!funcionarios.isEmpty()) {
	        return ResponseEntity.ok(funcionarios);
	    } else {
	        return ResponseEntity.notFound().build();
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

	private void validacaoPreenchimentoDTO(FuncionarioBarbeariaDTO funcionarioDTO) throws Exception {
		boolean usuarioTemTipoAdminBarbearia = roleUtil.possuiAdminBarbeariaRole();
        boolean usuarioTemTipoAdmin = roleUtil.possuiAdminRole();
		
        if (!usuarioTemTipoAdmin && !usuarioTemTipoAdminBarbearia) {
        	throw new Exception("Você não tem permissão para realizar essa ação!");
        }
        if (funcionarioDTO.getUsuarioId() == null) {
        	throw new Exception("Usuario não preenchido");
        }
        if (funcionarioDTO.getEstabelecimentoId() == null) {
        	throw new Exception("Estabelecimento não preenchido");
        }
	}
}
