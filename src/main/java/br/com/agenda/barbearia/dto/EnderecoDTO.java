package br.com.agenda.barbearia.dto;

import lombok.Data;

@Data
public class EnderecoDTO {
	private String logradouro;
	private String cidade;
	private String bairro;
	private String uf;
	private int numero;
	private String complemento;
}
