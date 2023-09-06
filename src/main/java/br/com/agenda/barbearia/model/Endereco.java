package br.com.agenda.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="tb_endereco")
@Data
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ds_logradouro")
	private String logradouro;
	
	@Column(name="ds_cidade")
	private String cidade;
	
	@Column(name="ds_bairro")
	private String bairro;
	
	@Column(name="ds_uf")
	private String uf;
	
	@Column(name="ds_numero")
	private int numero;
	
	@Column(name="ds_complemento")
	private String complemento;
}
