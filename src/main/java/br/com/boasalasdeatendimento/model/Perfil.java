package br.com.boasalasdeatendimento.model;

import javax.persistence.Entity;

@Entity
public class Perfil {

	private Integer id;
	private String nomePerfil;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomePerfil() {
		return nomePerfil;
	}
	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
}
