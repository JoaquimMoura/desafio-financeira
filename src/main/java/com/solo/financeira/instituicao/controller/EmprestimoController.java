package com.solo.financeira.instituicao.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.financeira.instituicao.domain.Emprestimo;
import com.solo.financeira.instituicao.repositories.filter.EmprestimoFilter;
import com.solo.financeira.instituicao.services.CadastroEmprestimoService;

@RestController
@RequestMapping("/api/emprestimo")
@CrossOrigin
public class EmprestimoController {

	private Logger logger = LoggerFactory.getLogger(EmprestimoController.class);

	@Autowired
	CadastroEmprestimoService emprestimoService;

	@PostMapping("/{identificador}")
	public ResponseEntity<?> criarNovoEmprestimo(@Valid @PathVariable String identificador,
			@RequestBody EmprestimoFilter filter) {

		logger.info("EMPRESTIMOCONTROLLER >> IN >> criarNovoEmprestimo");
		Emprestimo returnEmprestimo = emprestimoService.criarNovoEmprestimo(identificador, filter);

		logger.info("EMPRESTIMOCONTROLLER >> OUT >> criarNovoEmprestimo");

		return new ResponseEntity<Emprestimo>(returnEmprestimo, HttpStatus.CREATED);
	}
}
