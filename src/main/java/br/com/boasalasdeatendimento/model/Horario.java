package br.com.boasalasdeatendimento.model;

import java.util.Date;

public class Horario {
	
	private Integer id;
	private Date horario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getHorario() {
		return horario;
	}
	public void setHorario(Date horario) {
		this.horario = horario;
	}
}
