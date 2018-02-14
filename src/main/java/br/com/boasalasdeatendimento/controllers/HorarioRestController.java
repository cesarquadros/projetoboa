package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.boasalasdeatendimento.dao.HorarioDao;
import br.com.boasalasdeatendimento.model.ConsultaSala;
import br.com.boasalasdeatendimento.model.Horario;
import br.com.boasalasdeatendimento.util.DataUtil;

@RestController
public class HorarioRestController {
	
	@Autowired
	private HorarioDao horarioDao;

	@PostMapping(value = "/carregarhorariodisponivel")
	public ResponseEntity<List<Horario>> testeGet(@RequestBody ConsultaSala consultaSala) {

		List<Horario> listaHorario = new ArrayList<Horario>();

		consultaSala.setData(DataUtil.getDateFormat(consultaSala.getData(), "yyyyMMdd"));
		
		listaHorario = horarioDao.horariosDisponiveis(consultaSala);

		return new ResponseEntity<List<Horario>>(listaHorario, HttpStatus.OK);
	}
}
