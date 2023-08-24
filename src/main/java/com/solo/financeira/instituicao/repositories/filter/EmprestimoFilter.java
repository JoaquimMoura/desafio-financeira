package com.solo.financeira.instituicao.repositories.filter;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class EmprestimoFilter {

	@NotNull(message = "Identificador é obrigatório")
	private String identificador;

	@NotNull(message = "O valor desejado é obrigatório")
	private String valorDesejado;

	@NotNull(message = "A quantidade de parcelas é obrigatório")
	private Integer parcelas;
	
	private String valorPago;

}
