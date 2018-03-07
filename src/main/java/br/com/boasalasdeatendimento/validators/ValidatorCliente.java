package br.com.boasalasdeatendimento.validators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;

@Component
public class ValidatorCliente {
	
	public List<String>validarCliente(Cliente cliente, Autenticacao autenticacao) {
		
		List<String> listaErros = new ArrayList<String>();
		
		if(null == cliente.getNome()) {
			listaErros.add("NOME");
		}
		
		if(null == cliente.getSobrenome()) {
			listaErros.add("SOBRENOME");
		}
		
		if (null ==cliente.getEmail()) {
			listaErros.add("EMAIL");
		}
		
		if(null == autenticacao.getSenha()) {
			listaErros.add("SENHA");
		}
		
		if(null ==cliente.getTelCelular()) {
			listaErros.add("CELULAR");
		}
		
		if(null ==cliente.getTelCelular()) {
			listaErros.add("TELEFONE FIXO");
		}
		
		if (null ==cliente.getCpf()) {
			listaErros.add("CPF");
		}
		
		if (null == cliente.getSexo() || (!cliente.getSexo().equals("M") && !cliente.getSexo().equals("F"))) {
			listaErros.add("SEXO");
		}
		return listaErros;		
	}
}
