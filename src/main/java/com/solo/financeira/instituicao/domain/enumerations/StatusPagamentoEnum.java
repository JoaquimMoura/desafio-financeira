package com.solo.financeira.instituicao.domain.enumerations;

public enum StatusPagamentoEnum {

	PAGO(0), PENDENTE(1), NAO_RECONHECIDO(null);

	private Integer statusId;

	StatusPagamentoEnum(Integer statusId) {
		this.statusId = statusId;
	}

	public static StatusPagamentoEnum fromStatusId(Integer statusId) {
		for (StatusPagamentoEnum status : StatusPagamentoEnum.values()) {
			if (status.statusId != null && status.statusId == statusId) {
				return status;
			}
		}
		return NAO_RECONHECIDO;
	}

}