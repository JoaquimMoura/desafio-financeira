package com.solo.financeira.instituicao.controller;

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
import com.solo.financeira.instituicao.services.PagamentoEmprestimoService;

@RestController
@RequestMapping("/api/pagamento")
@CrossOrigin
public class PagamentoController {

	private Logger logger = LoggerFactory.getLogger(PagamentoController.class);

	@Autowired
	PagamentoEmprestimoService pagamentoService;

	@PostMapping("/{idPacela}")
	public ResponseEntity<?> pagmentoParcela(@PathVariable Long idPacela, @RequestBody EmprestimoFilter filter) {

		logger.info("PAGAMENTOCONTROLLER >> IN >> PAGMENTO PARCELA");

		Emprestimo returnEmprestimo = pagamentoService.pagmentoParcela(idPacela, filter);

		logger.info("PAGAMENTOCONTROLLER >> OUT >> PAGMENTO PARCELA");

		return new ResponseEntity<Emprestimo>(returnEmprestimo, HttpStatus.OK);
	}
}