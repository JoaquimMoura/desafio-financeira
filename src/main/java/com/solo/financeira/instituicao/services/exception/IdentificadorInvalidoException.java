package com.solo.financeira.instituicao.services.exception;

public class IdentificadorInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdentificadorInvalidoException(String message) {
		super(message);
	}
	public IdentificadorInvalidoException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
