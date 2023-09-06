package br.com.agenda.barbearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.agenda.barbearia.model.TipoUsuario;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Long>{
	@Query("SELECT COUNT(t.id) > 0 FROM TipoUsuario t WHERE t.tipo = :tipo")
    boolean existeTipo(String tipo);
	
	@Query("SELECT t.tipo FROM TipoUsuario t WHERE t.tipo = :tipo")
	TipoUsuario findByNomeTipo(String tipo);
}
