package br.com.agenda.barbearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.agenda.barbearia.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
