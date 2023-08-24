package com.solo.financeira.instituicao.domain.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.solo.financeira.instituicao.domain.Pessoa;
import com.solo.financeira.instituicao.domain.validation.group.PessoaFisica;

public class PessoaGroupSequenceProvider implements DefaultGroupSequenceProvider<Pessoa> {

	@Override
	public List<Class<?>> getValidationGroups(Pessoa pessoa) {

		List<Class<?>> groups = new ArrayList<>();
		groups.add(Pessoa.class);

		if (isPessoaSelecionada(pessoa)) {

			switch (pessoa.getTipoIdentificador().getDescricao()) {

			case "PF":
				groups.add(PessoaFisica.class);
				break;
			case "PJ":
				groups.add(pessoa.getTipoIdentificador().getGroup());
				break;
			case "EU":
				groups.add(pessoa.getTipoIdentificador().getGroup());
				break;
			case "AP":
				groups.add(pessoa.getTipoIdentificador().getGroup());
				break;

			default:
				break;
			}

		}

		return groups;
	}

	protected boolean isPessoaSelecionada(Pessoa pessoa) {
		return pessoa != null && pessoa.getTipoIdentificador() != null;
	}

}