package com.solo.financeira.instituicao.services;

import static com.solo.financeira.instituicao.mapper.EmprestimoMapper.INSTANCE;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.financeira.instituicao.controller.PagamentoController;
import com.solo.financeira.instituicao.domain.Emprestimo;
import com.solo.financeira.instituicao.domain.enumerations.StatusPagamentoEnum;
import com.solo.financeira.instituicao.repositories.EmprestimoRepository;
import com.solo.financeira.instituicao.repositories.filter.EmprestimoFilter;
import com.solo.financeira.instituicao.services.exception.ParcelaNaoExisteException;
import com.solo.financeira.instituicao.utils.Utils;

@Service
public class PagamentoEmprestimoService {

	private Logger logger = LoggerFactory.getLogger(PagamentoEmprestimoService.class);

	@Autowired
	EmprestimoRepository emprestimoRepository;

	public Emprestimo pagmentoParcela(Long codigo, EmprestimoFilter filter) {

		logger.info("PAGAMENTO CONTROLLER >> IN >> PAGMENTO PARCELA");

		var emprestimo = emprestimoRepository.findEmprestimoById(codigo);

		var mapper = INSTANCE.toDomain(filter);

		if (emprestimo.isPresent()) {

			emprestimo.get().setDataPagamento(Utils.getDate());
			emprestimo.get().setStatus(StatusPagamentoEnum.PAGO);
			var qtdParcelas = emprestimo.get().getQtdParcelas();

			var valorRestante = emprestimo.get().getValorTotal().subtract(mapper.getValorParcela());

			if (valorRestante != null) {
				emprestimo.get().setValorTotal(valorRestante);
			}

			qtdParcelas = qtdParcelas - 1;

			emprestimo.get().setQtdParcelas(qtdParcelas);

			emprestimoRepository.save(emprestimo.get());

			logger.info("PAGAMENTO CONTROLLER >> OUT >> PAGMENTO PARCELA");

		} else {
			throw new ParcelaNaoExisteException("Parcela de código " + codigo + " não encontrada. ");
		}
		return emprestimo.get();
	}

}
