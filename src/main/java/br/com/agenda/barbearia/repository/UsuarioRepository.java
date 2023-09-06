package br.com.agenda.barbearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.agenda.barbearia.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	Usuario findByEmail(String email);

	@Query("SELECT u.senha FROM Usuario u WHERE u.email = ?1")
	String getSenhaCriptografadaPorEmail(String email);

}
