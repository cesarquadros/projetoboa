package br.com.boasalasdeatendimento.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	public static String getDataAtualSemSeparador() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy hhmmss");
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
	
	public static Date getTimestamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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

	public static Date convertStringToDateTimeStamp(String data, String hora) {

		String dataString = getDataStringToString(data+" "+hora);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		Date dataConvertida = new Date();

		try {

			dataConvertida = formatter.parse(dataString);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return dataConvertida;
	}
	
	public static String getTimeDiff(final Date dataInicio, final Date dataFim) {
		long time = dataFim.getTime() - dataInicio.getTime();

		Integer hora = (int) (time / ((1000 * 60) * 60));
		time = time % ((1000 * 60) * 60);
		Integer minutos = (int) (time / (1000 * 60));
		time = time % (1000 * 60);
		Integer segundos = (int) (time / 1000);

		return padLeft(hora.toString(), 2, "0") + ":"
				+ padLeft(minutos.toString(), 2, "0") + ":"
				+ padLeft(segundos.toString(), 2, "0");
	}

	public static String getDataStringToString(final String data) {

		String dataReplace = data.replaceAll("-", "/");
		String[] s = dataReplace.split("/");
		String dataFormatada = s[0] + "-" + s[1] + "-" + s[2];

		return dataFormatada;

	}
	/**
	 * Método utilizado para adicionar caracteres a esquerda do texto 
	 * @param iniText texto inicial.
	 * @param tamanho tamanho máximo do campo
	 * @param carac caracter a ser adicionado
	 * @return texto editado
	 */
	public static String padLeft(final String iniText, final int tamanho, final String carac){
		String texto = iniText;
		while(texto.length() < tamanho){
			texto = carac.concat(texto); 
		}
		return texto;
	}
	
}
