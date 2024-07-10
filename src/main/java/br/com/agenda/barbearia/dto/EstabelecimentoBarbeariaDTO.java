package br.com.agenda.barbearia.dto;

import br.com.agenda.barbearia.interfaces.ValidarCampoPreenchido;
import lombok.Data;

@Data
public class EstabelecimentoBarbeariaDTO {
	@ValidarCampoPreenchido
	private String nome;
	private EnderecoDTO endereco;
}
