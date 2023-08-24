package com.solo.financeira.instituicao.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.financeira.instituicao.domain.Pessoa;
import com.solo.financeira.instituicao.mapper.PessoaMapper;
import com.solo.financeira.instituicao.repositories.PessoaRepository;
import com.solo.financeira.instituicao.repositories.filter.PessoaFilter;

@Service
public class CadastroPessoaService {

	private org.slf4j.Logger logger = LoggerFactory.getLogger(CadastroPessoaService.class);

	@Autowired
	PessoaRepository pessoaRepository;

	public Pessoa cadastro(PessoaFilter filter) {

		logger.info("CADASTRO IN -> Rota para criar cadastro de Pessoa FILTER: {}", filter);

		var pessoa = PessoaMapper.INSTANCE.toDomain(filter);

		var identificador = Pessoa.formataDados(pessoa.getIdentificador());

		Pessoa.tipoIdentificador(pessoa, identificador.length());

		pessoaRepository.save(pessoa);

		logger.info("CADASTRO OUT -> Rota para criar cadastro de Pessoa FILTER: {}", pessoa);

		return pessoa;
	}

}
