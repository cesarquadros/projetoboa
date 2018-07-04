package br.com.boasalasdeatendimento.util;

import java.util.HashMap;
import java.util.Map;

public final class Util {
	
	public static Map<Integer, String> mapStatus = new HashMap<Integer, String>();

	public static Integer ABERTO = 1;
	public static Integer FINALIZADO = 2;
	public static Integer CANCELADO = 3;

	static {

		mapStatus.put(1, "ABERTO");
		mapStatus.put(2, "FINALIZADO");
		mapStatus.put(3, "CANCELADO");

	}
}
