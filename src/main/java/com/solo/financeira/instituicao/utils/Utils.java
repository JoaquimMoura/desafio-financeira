package com.solo.financeira.instituicao.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

	public static String getDate() {

		LocalDateTime agora = LocalDateTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		return agora.format(formatter);
	}

}
