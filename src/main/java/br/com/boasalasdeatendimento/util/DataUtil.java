package br.com.boasalasdeatendimento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public static String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
