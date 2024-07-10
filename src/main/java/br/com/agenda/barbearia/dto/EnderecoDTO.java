package br.com.agenda.barbearia.dto;

import br.com.agenda.barbearia.interfaces.ValidarCampoPreenchido;
import lombok.Data;

@Data
public class EnderecoDTO {
	@ValidarCampoPreenchido
	private String logradouro;
	@ValidarCampoPreenchido
	private String cidade;
	@ValidarCampoPreenchido
	private String bairro;
	@ValidarCampoPreenchido
	private String uf;
	@ValidarCampoPreenchido
	private int numero;
	private String complemento;
}
