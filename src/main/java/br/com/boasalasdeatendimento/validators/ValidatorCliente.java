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
			listaErros.add("Campo NOME é obrigatório");
		}
		
		if(null == cliente.getSobrenome()) {
			listaErros.add("Campo SOBRENOME é obrigatório");
		}
		
		if (null ==cliente.getEmail()) {
			listaErros.add("Campo EMAIL é obrigatório");
		}
		
		if(null == autenticacao.getSenha()) {
			listaErros.add("Campo SENHA é obrigatório");
		}
		
		if(null ==cliente.getTelCelular()) {
			listaErros.add("Campo TELEFONE CELULAR é obrigatório");
		}
		
		if(null ==cliente.getTelCelular()) {
			listaErros.add("Campo TELEFONE FIXO é obrigatório");
		}
		
		if (null ==cliente.getCpf()) {
			listaErros.add("Campo CPF FIXO é obrigatório");
		}
		
		if (null == cliente.getSexo() || (!cliente.getSexo().equals("M") && !cliente.getSexo().equals("F"))) {
			listaErros.add("Campo SEXO é obrigatório");
		}
		return listaErros;		
	}
}
