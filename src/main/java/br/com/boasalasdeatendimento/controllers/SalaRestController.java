package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.boasalasdeatendimento.dao.UnidadeDao;
import br.com.boasalasdeatendimento.model.ConsultaSala;
import br.com.boasalasdeatendimento.model.Unidade;

@RestController
public class SalaRestController {

	@PostMapping(value = "/carregarsalas", consumes = {"application/json;charset=UTF-8"} )
	public ResponseEntity<List<Unidade>> carregarSalas(@RequestBody ConsultaSala consultaSala) {

		List<Unidade> listaUnidade = new ArrayList<Unidade>();

		UnidadeDao unidadeDao = new UnidadeDao();

		listaUnidade = unidadeDao.listaUnidades();

		return ResponseEntity.ok(listaUnidade);
	}
	
	@GetMapping(value = "/unidades")
	public ResponseEntity<List<Unidade>> getSalas() {

		List<Unidade> listaUnidade = new ArrayList<Unidade>();

		UnidadeDao unidadeDao = new UnidadeDao();

		listaUnidade = unidadeDao.listaUnidades();

		return ResponseEntity.ok(listaUnidade);
	}
}
