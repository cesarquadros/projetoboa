package br.com.boasalasdeatendimento.model;

import java.util.Date;

public class Agendamento {

	private Integer id;
	private Horario horario;
	private Cliente cliente;
	private Sala sala;
	private Date dataAgendamento;
	private String dataAgendamentoString;
	private Boolean status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Horario getHorario() {
		return horario;
	}
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Sala getSala() {
		return sala;
	}
	public void setSala(Sala sala) {
		this.sala = sala;
	}
	public Date getDataAgendamento() {
		return dataAgendamento;
	}
	public void setDataAgendamento(Date dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}
	public String getDataAgendamentoString() {
		return dataAgendamentoString;
	}
	public void setDataAgendamentoString(String dataAgendamentoString) {
		this.dataAgendamentoString = dataAgendamentoString;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
