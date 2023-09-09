package br.com.agenda.barbearia.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "tb_funcionario_barbearia")
@Data
public class FuncionarioBarbearia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nm_funcionario")
	private String nome;

	@Column(name = "ft_funcionario")
	private Blob foto;
	
	@ManyToOne
	@JoinColumn(name = "fk_id_estabelecimento_barbearia")
	private EstabelecimentoBarbearia estabelecimentoBarbearia;
	
	@OneToOne
	@JoinColumn(name = "fk_id_usuario")
	private Usuario usuario;
}
