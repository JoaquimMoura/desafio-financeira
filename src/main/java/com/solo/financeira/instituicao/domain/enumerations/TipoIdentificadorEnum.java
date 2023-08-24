package com.solo.financeira.instituicao.domain.enumerations;

import java.math.BigDecimal;

import com.solo.financeira.instituicao.domain.validation.group.Aposentado;
import com.solo.financeira.instituicao.domain.validation.group.EstudanteUniversitario;
import com.solo.financeira.instituicao.domain.validation.group.PessoaFisica;
import com.solo.financeira.instituicao.domain.validation.group.PessoaJuridica;

public enum TipoIdentificadorEnum {

	PF("PESSOA FISICA", "000.000.000-00", new BigDecimal("300.00"), new BigDecimal("10000.00"), PessoaFisica.class) {
		@Override
		public String formatar(String numero) {
			return numero.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-");
		}
	},

	PJ("PESSOA JURIDICA", "00.000.000/0000-00", new BigDecimal("1000.00"), new BigDecimal("100000.00"),
			PessoaJuridica.class) {
		@Override
		public String formatar(String numero) {
			return numero.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-");
		}
	},

	EU("ESTUDANTE UNIVERSITARIO", "00000000", new BigDecimal("100.00"), new BigDecimal("10000.00"),
			EstudanteUniversitario.class) {
		@Override
		public String formatar(String numero) {
			return numero.replaceAll("(\\d{8})", "$1");
		}
	},

	AP("APOSENTADO", "0000000000", new BigDecimal("100.00"), new BigDecimal("10000.00"), Aposentado.class) {
		@Override
		public String formatar(String numero) {
			return numero.replaceAll("(\\d{10})", "$1");
		}
	};

	private final String descricao;
	private final String mascara;
	private final BigDecimal valorMinimo;
	private final BigDecimal valorMaximo;
	private final Class<?> group;

	private TipoIdentificadorEnum(String descricao, String mascara, BigDecimal valorMinimo, BigDecimal valorMaximo,
			Class<?> group) {
		this.descricao = descricao;
		this.mascara = mascara;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.group = group;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getMascara() {
		return mascara;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public Class<?> getGroup() {
		return group;
	}

	public static String removerFormatacao(String numero) {
		return numero.replaceAll("\\.|-|/", "");
	}

	public abstract String formatar(String numero);

}
