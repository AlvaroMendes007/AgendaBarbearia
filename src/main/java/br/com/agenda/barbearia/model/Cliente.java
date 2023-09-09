package br.com.agenda.barbearia.model;

import java.util.Date;

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
@Table(name = "tb_cliente")
@Data
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nm_cliente")
	private String nome;

	@Column(name = "dt_nascimento")
	private Date dataNascimento;
		
	@OneToOne
	@JoinColumn(name = "fk_id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "fk_id_endereco")
	private Endereco enderecoCliente;
}
