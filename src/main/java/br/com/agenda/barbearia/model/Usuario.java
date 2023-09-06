package br.com.agenda.barbearia.model;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ds_email")
	private String email;

	@Column(name = "ds_senha")
	private String senha;

	@ManyToOne
	@JoinColumn(name = "fk_id_tipo_usuario")
	private TipoUsuario tipoUsuario;

	public Usuario(String email, String senha, TipoUsuario tipoUsuario) {
		this.senha = senha;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
	}

	public Usuario(String email, String senha) {
		this.senha = senha;
		this.email = email;
	}
	
	public Usuario() {
	}

	public Collection<? extends GrantedAuthority> getAcesso() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+tipoUsuario.getTipo()));
	}
}
