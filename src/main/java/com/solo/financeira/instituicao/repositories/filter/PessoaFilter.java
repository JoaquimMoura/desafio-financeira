package com.solo.financeira.instituicao.repositories.filter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.stereotype.Component;

import com.solo.financeira.instituicao.domain.validation.group.PessoaFisica;
import com.solo.financeira.instituicao.domain.validation.group.PessoaJuridica;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PessoaFilter {

	@Size(max = 50, message = "Só é pertido 50 caracteres")
	@NotNull(message = "Nome é obrigatório")
	private String nome;

	@CPF(groups = PessoaFisica.class)
	@CNPJ(groups = PessoaJuridica.class)
	@NotNull(message = "Identificador é obrigatório")
	@Size(max = 50, message = "Só é pertido 50 caracteres")
	private String identificador;

	@NotNull(message = "Data de Nascimento é obrigatório")
	private String dataNascimento;

}
