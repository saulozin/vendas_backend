package com.saulo.vendasapi.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateUtils {
	
	public static final Date DATA_INICIO_PADRAO;
	
	static {
		DATA_INICIO_PADRAO = DateUtils.fromString("01/01/1970");
	}
	
	public static Date hoje(boolean atEndOfDay) {
		String dataHojeFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		return fromString(dataHojeFormatada, atEndOfDay);
	}
	
	public static Date fromString(String dataString) {
		return fromString(dataString, false);
	}
	
	public static Date fromString(String dataString, boolean atEndOfDay) {
		if(!StringUtils.hasText(dataString)) {
			return null;
		}
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataLocalDate = LocalDate.parse(dataString, formatter);
		//var dataLocalDate = LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		LocalDateTime dataHora;
		if (atEndOfDay) {
			dataHora = dataLocalDate.atTime(LocalTime.of(23, 59));
		} else {
			dataHora = dataLocalDate.atTime(LocalTime.of(0, 0));
		}
		
		Instant instant = dataHora.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
	
	// Método adicional para converter para o formato desejado "yyyy-MM-ddTHH:mm:ssZ"
	public static String toInstantIsoFormat(Date date) {
		Instant instant = date.toInstant();
		return instant.toString();
	}
}
