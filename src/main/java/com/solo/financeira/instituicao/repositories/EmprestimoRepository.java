package com.solo.financeira.instituicao.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solo.financeira.instituicao.domain.Emprestimo;
import com.solo.financeira.instituicao.domain.Pessoa;

@Repository
public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {

	Optional<Iterable<Emprestimo>> findEmprestimosByPessoa(Pessoa pessoa);

	Optional<Emprestimo> findEmprestimoById(Long id);
	
	Optional<Emprestimo> findEmprestimoById(String cpfOuCnpj);

}
