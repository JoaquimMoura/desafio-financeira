package com.solo.financeira.instituicao.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solo.financeira.instituicao.domain.Pessoa;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

	Iterable<Pessoa> findAll();

	Optional<Pessoa> findPessoaById(Long id);

	Optional<Pessoa> findPessoaByIdentificador(String identificador);
}
