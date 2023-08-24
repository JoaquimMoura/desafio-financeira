package com.solo.financeira.instituicao.services.exception;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class StandartError implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String msg;
	private Long timeStamp;

}
