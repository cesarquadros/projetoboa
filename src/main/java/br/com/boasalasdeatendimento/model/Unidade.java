package br.com.boasalasdeatendimento.model;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class Unidade {

	private Integer id;
	private Endereco endereco;
	private String nomeUnidade;
	private List<Sala> listaSala;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public String getNomeUnidade() {
		return nomeUnidade;
	}
	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}
	public List<Sala> getListaSala() {
		return listaSala;
	}
	public void setListaSala(List<Sala> listaSala) {
		this.listaSala = listaSala;
	}
	
}
