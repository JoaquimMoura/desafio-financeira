package com.solo.financeira.instituicao.services.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResouceExceptionHandler {

	@ExceptionHandler(BussinessException.class)
	public ResponseEntity<StandartError> preconditionFailed(BussinessException e, HttpServletRequest request) {

		StandartError err = new StandartError();
		err.setMsg(e.getMessage());
		err.setStatus(HttpStatus.PRECONDITION_FAILED.value());
		err.setTimeStamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(err);
	}

	@ExceptionHandler(PessoaNaoExisteException.class)
	public ResponseEntity<StandartError> objectNotFound(PessoaNaoExisteException e, HttpServletRequest request) {

		StandartError err = new StandartError();
		err.setMsg(e.getMessage());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setTimeStamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(ParcelaNaoExisteException.class)
	public ResponseEntity<StandartError> objectNotFound(ParcelaNaoExisteException e, HttpServletRequest request) {

		StandartError err = new StandartError();
		err.setMsg(e.getMessage());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setTimeStamp(System.currentTimeMillis());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
}
