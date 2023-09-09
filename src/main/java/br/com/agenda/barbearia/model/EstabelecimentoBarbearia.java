package br.com.agenda.barbearia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_estabelecimento_barbearia")
@Data
public class EstabelecimentoBarbearia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nm_barbearia")
	private String nome;
	
	@OneToOne
	@JoinColumn(name = "fk_id_endereco")
	private Endereco enderecoBarbearia;
}
