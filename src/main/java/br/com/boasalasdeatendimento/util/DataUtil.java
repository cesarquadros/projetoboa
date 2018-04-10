package br.com.boasalasdeatendimento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;

public class DataUtil {

	public static String getDateFormatString(String dataString, String formatoEntrada,String formatoSaida) {

		Date date;

		try {
			date = new SimpleDateFormat(formatoEntrada).parse(dataString);
			return new SimpleDateFormat(formatoSaida).format(date);

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date stringToDate(String dataString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date date =  dateFormat.parse(dataString);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDataAtual() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static Date getDataAtualDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		String dataString = dateFormat.format(date);
		
		try {
			Date data = dateFormat.parse(dataString);
			return data;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getHoraAtual() {
		
		String TIME_ZONE = "America/Sao_Paulo";
		
		TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));  
        DateTimeZone.setDefault(DateTimeZone.forID(TIME_ZONE));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
