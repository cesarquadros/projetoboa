package br.com.boasalasdeatendimento.model;

import java.util.Date;

public class Cliente {

	private Integer id;
	private Autenticacao autenticacao;
	private String nome;
	private String sobrenome;
	private String telFixo;
	private String telCelular;
	private String cpf;
	private String email;
	private String sexo;
	private Date dataNascimento;
	private String dataNascimentoString;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Autenticacao getAutenticacao() {
		return autenticacao;
	}
	public void setAutenticacao(Autenticacao autenticacao) {
		this.autenticacao = autenticacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSobrenome() {
		return sobrenome;
	}
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	public String getTelFixo() {
		return telFixo;
	}
	public void setTelFixo(String telFixo) {
		this.telFixo = telFixo;
	}
	public String getTelCelular() {
		return telCelular;
	}
	public void setTelCelular(String telCelular) {
		this.telCelular = telCelular;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getDataNascimentoString() {
		return dataNascimentoString;
	}
	public void setDataNascimentoString(String dataNascimentoString) {
		this.dataNascimentoString = dataNascimentoString;
	}
}
