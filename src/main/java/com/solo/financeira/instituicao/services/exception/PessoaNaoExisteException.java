package com.solo.financeira.instituicao.services.exception;

public class PessoaNaoExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PessoaNaoExisteException(String message) {
		super(message);
	}

}
