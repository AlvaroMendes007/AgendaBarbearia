package br.com.agenda.barbearia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
	@Cascade(CascadeType.DELETE)
	private Endereco enderecoBarbearia;
	
	@OneToMany
	@Cascade(CascadeType.ALL)
    private List<FuncionarioBarbearia> funcionarios = new ArrayList<>();
	
	public EstabelecimentoBarbearia () {}

	public EstabelecimentoBarbearia(String nome, Endereco enderecoBarbearia) {
		this.nome = nome;
		this.enderecoBarbearia = enderecoBarbearia;
	}
}
