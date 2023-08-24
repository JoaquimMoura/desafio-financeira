package com.solo.financeira.instituicao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.financeira.instituicao.domain.Pessoa;
import com.solo.financeira.instituicao.repositories.PessoaRepository;
import com.solo.financeira.instituicao.repositories.filter.PessoaFilter;
import com.solo.financeira.instituicao.services.CadastroPessoaService;

@RestController
@RequestMapping("/api/pessoa")
@CrossOrigin
public class PessoaController {

	private Logger logger = LoggerFactory.getLogger(PessoaController.class);

	@Autowired
	CadastroPessoaService pessoaService;

	@Autowired
	PessoaRepository pessoaRepository;

	@PostMapping
	public ResponseEntity<?> cadastroPessoa(@RequestBody PessoaFilter filter) {

		logger.info("PESSOACONTROLLER >> CADASTRO DE PESSOA");
		return new ResponseEntity<Pessoa>(pessoaService.cadastro(filter), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Iterable<Pessoa>> buscarPessoas() {

		logger.info("PESSOACONTROLLER >> BUSCAR PESSOAS");
		Iterable<Pessoa> pessoas = pessoaRepository.findAll();

		return new ResponseEntity<Iterable<Pessoa>>(pessoas, HttpStatus.OK);
	}

	@GetMapping("/{identificador}")
	public ResponseEntity<Pessoa> buscarPessoaByIdentificador(@PathVariable String identificador) {

		logger.info("PESSOACONTROLLER >> BUSCAR PESSOA BY IDENTIFICADOR");
		return pessoaRepository.findPessoaByIdentificador(identificador)
				.map(response -> ResponseEntity.ok().body(response)).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirPessoa(@PathVariable Long id) {

		logger.info("PESSOACONTROLLER >> EXCLUIR PESSOA");
		return pessoaRepository.findById(id).map(response -> {
			pessoaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}

}
