package br.com.agenda.barbearia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.agenda.barbearia.model.FuncionarioBarbearia;

public interface FuncionarioBarbeariaRepository extends JpaRepository<FuncionarioBarbearia, Long> {
	
	@Query("SELECT f FROM FuncionarioBarbearia f join Usuario u on f.usuario = u.id join TipoUsuario tp on u.tipoUsuario = tp.id WHERE tp.id = ?1")
	List<FuncionarioBarbearia> findByTipo(int id);
}
