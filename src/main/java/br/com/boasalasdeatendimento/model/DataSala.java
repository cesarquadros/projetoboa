package br.com.boasalasdeatendimento.model;

import java.util.Date;

public class DataSala {
	
	private Integer numero;
	private Date data;
	
	public DataSala() {
		super();
	}
	
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	
}
