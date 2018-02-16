package br.com.boasalasdeatendimento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.dao.ClienteDao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.Status;

@RestController
public class ClienteController {
	
	@Autowired
	private AutenticarDao autenticarDao;
	
	@Autowired
	private ClienteDao clienteDao;

	@PostMapping(value = "/cadastrarcliente")
	public ResponseEntity<Status> testeGet(@RequestBody Cliente cliente) {
		
		cliente.getAutenticacao().setUsuario(cliente.getEmail());
		cliente.getAutenticacao().setId(autenticarDao.inserir(cliente.getAutenticacao()));
		
		clienteDao.inserir(cliente);
		
		return ResponseEntity.ok(null);
	}
}
