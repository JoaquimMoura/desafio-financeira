package com.solo.financeira.instituicao.services;

import static com.solo.financeira.instituicao.domain.Emprestimo.validaCnpj;
import static com.solo.financeira.instituicao.domain.Emprestimo.validaCpf;
import static com.solo.financeira.instituicao.domain.Emprestimo.validaDadosEstudante;
import static com.solo.financeira.instituicao.domain.enumerations.StatusPagamentoEnum.PENDENTE;
import static com.solo.financeira.instituicao.domain.enumerations.TipoIdentificadorEnum.AP;
import static com.solo.financeira.instituicao.domain.enumerations.TipoIdentificadorEnum.EU;
import static com.solo.financeira.instituicao.domain.enumerations.TipoIdentificadorEnum.PF;
import static com.solo.financeira.instituicao.domain.enumerations.TipoIdentificadorEnum.PJ;
import static com.solo.financeira.instituicao.mapper.EmprestimoMapper.INSTANCE;
import static com.solo.financeira.instituicao.utils.Utils.getDate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.financeira.instituicao.domain.Emprestimo;
import com.solo.financeira.instituicao.domain.Pessoa;
import com.solo.financeira.instituicao.domain.enumerations.TipoIdentificadorEnum;
import com.solo.financeira.instituicao.repositories.EmprestimoRepository;
import com.solo.financeira.instituicao.repositories.PessoaRepository;
import com.solo.financeira.instituicao.repositories.filter.EmprestimoFilter;
import com.solo.financeira.instituicao.services.exception.BussinessException;
import com.solo.financeira.instituicao.services.exception.IdentificadorInvalidoException;
import com.solo.financeira.instituicao.services.exception.PessoaNaoExisteException;

@Service
public class CadastroEmprestimoService {

	private Logger logger = LoggerFactory.getLogger(CadastroEmprestimoService.class);

	EmprestimoRepository emprestimoRepository;
	PessoaRepository pessoaRepository;

	@Autowired
	public CadastroEmprestimoService(EmprestimoRepository emprestimoRepository, PessoaRepository pessoaRepository) {
		this.emprestimoRepository = emprestimoRepository;
		this.pessoaRepository = pessoaRepository;
	}

	public Emprestimo criarNovoEmprestimo(String identificador, EmprestimoFilter filter) {

		logger.info("CADASTROEMPRESTIMOSERVICE >> IN >> CRIARNOVOEMPRESTIMO");

		var emprestimo = INSTANCE.toDomain(filter);

		var retorno = Boolean.FALSE;

		var pessoa = pessoaRepository.findPessoaByIdentificador(identificador);

		logger.info("CADASTROEMPRESTIMOSERVICE >> IN >> findPessoaByIdentificador");

		if (!pessoa.isPresent()) {
			throw new PessoaNaoExisteException("A Pessoa com o identificador " + identificador + " nao exite. ");
		}

		logger.info("CADASTROEMPRESTIMOSERVICE >> OUT >> findPessoaByIdentificador");

		if (pessoa.get().getTipoIdentificador().equals(PF)) {

			logger.info("CADASTROEMPRESTIMOSERVICE >> Validar identificador PESSOA FISICA. ");

			retorno = validaCpf(identificador);
		}

		if (pessoa.get().getTipoIdentificador().equals(PJ)) {

			logger.info("CADASTROEMPRESTIMOSERVICE >> Validar identificador PESSOA JURIDICA. ");
			retorno = validaCnpj(identificador);
		}

		if (pessoa.get().getTipoIdentificador().equals(EU)) {

			logger.info("CADASTROEMPRESTIMOSERVICE >> Validar identificador ESTUDANTE UNIVERSITARIO. ");
			retorno = validaDadosEstudante(identificador);
		}

		if (pessoa.get().getTipoIdentificador().equals(AP)) {

			logger.info("CADASTROEMPRESTIMOSERVICE >> Validar identificador APOSENTADO. ");
			retorno = Emprestimo.validaDadosAposentado(identificador);
		}

		if (!retorno) {
			throw new IdentificadorInvalidoException("Identificador invalido: " + identificador);
		}

		logger.info("CADASTROEMPRESTIMOSERVICE >> IN >> COMPARAR LIMTE MAXIMO DE EMPRESTIMO. ");

		var valorMaximoPermitido = comparaLimteMaximoEmprestimo(emprestimo, pessoa);

		logger.info("CADASTROEMPRESTIMOSERVICE >> OUT >> COMPARAR LIMTE MAXIMO DE EMPRESTIMO. ");

		/*
		 *
		 * O comparaLimteEmprestimo retorna 3 valores:
		 * 
		 * -1: quando vc compare seu numero com um valor maior. 0: quando vc compara seu
		 * numero com um numero igual. 1 : quando vc compara seu numero com um valor
		 * menor.
		 * 
		 */

		if (valorMaximoPermitido == -1) {
			throw new BussinessException("Valor do emprestimo solicitado é maior que permitido.");
		}

		var valorMinimoPermitido = comparaLimteMinimoEmprestimo(emprestimo, pessoa);

		if (valorMinimoPermitido == 1) {
			throw new BussinessException("Valor do emprestimo solicitado é menor que permitido.");
		}

		if (emprestimo.getParcelas() > 24) {
			throw new BussinessException("A Quantidade de Parcelas não pode ser superio");
		}

		emprestimo.setJuros(getJuros(pessoa.get().getTipoIdentificador()));

		var valorParcela = calcularValorTotal(emprestimo).divide(new BigDecimal(filter.getParcelas()));

		logger.info("CADASTROEMPRESTIMOSERVICE >> OUT >> CALCULAR JUROS COMPOSTO. ");

		emprestimo.setPessoa(pessoa.get());
		emprestimo.setValorTotal(calcularValorTotal(emprestimo));
		emprestimo.setStatus(PENDENTE);
		emprestimo.setDataEmprestimo(getDate());

		emprestimo.setQtdParcelas(Long.valueOf(filter.getParcelas()));
		emprestimo.setValorParcela(valorParcela);

		emprestimoRepository.save(emprestimo);

		logger.info("CADASTROEMPRESTIMOSERVICE >> OUT >> CRIARNOVOEMPRESTIMO");

		return emprestimo;
	}

	private int comparaLimteMaximoEmprestimo(Emprestimo emprestimo, Optional<Pessoa> pessoa) {
		return pessoa.get().getTipoIdentificador().getValorMaximo().compareTo(emprestimo.getValorContratado());
	}

	private int comparaLimteMinimoEmprestimo(Emprestimo emprestimo, Optional<Pessoa> pessoa) {
		return pessoa.get().getTipoIdentificador().getValorMinimo().compareTo(emprestimo.getValorContratado());
	}

	public BigDecimal calcularValorTotal(Emprestimo emprestimo) {

		logger.info("CADASTROEMPRESTIMOSERVICE >> IN >> CALCULAR JUROS COMPOSTO. ");

		var principal = emprestimo.getValorContratado();
		var taxa = emprestimo.getJuros();
		var meses = emprestimo.getParcelas();

		// Calculo do juros composto: M = C x (1 + i)^t - M = Montante, C = Valor
		// inicial, i = juros, t = prazo
		return principal.multiply(BigDecimal.ONE.add(taxa.multiply(new BigDecimal(meses))));
	}

	// Seta o juros do emprestimo baseado no risco do cliente.
	public BigDecimal getJuros(TipoIdentificadorEnum risco) {

		logger.info("CADASTROEMPRESTIMOSERVICE >> BUSCAR VALOR DE JUROS. ");

		var mc = new MathContext(2);

		switch (risco) {
		case PF:
			return new BigDecimal(1.4).round(mc);
		case PJ:
			return new BigDecimal(1.5).round(mc);
		case EU:
			return new BigDecimal(1.0).round(mc);
		case AP:
			return new BigDecimal(0.7).round(mc);
		default:
			throw new RuntimeException("valor inválido!");
		}

	}

}
