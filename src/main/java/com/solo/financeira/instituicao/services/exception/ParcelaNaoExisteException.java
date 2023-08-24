package com.solo.financeira.instituicao.services.exception;

public class ParcelaNaoExisteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ParcelaNaoExisteException(String message) {
		super(message);
	}

}
