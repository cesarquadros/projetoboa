package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.boasalasdeatendimento.model.Cliente;

@RestController
public class TestRestController {

    @GetMapping("/rest")
    public List<Cliente>testeGet() {
    	
    	List<Cliente> listaCliente = new ArrayList<Cliente>();
    	
        return listaCliente;
    }
}
