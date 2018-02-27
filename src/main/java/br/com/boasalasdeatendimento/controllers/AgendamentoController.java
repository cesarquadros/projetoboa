package br.com.boasalasdeatendimento.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.boasalasdeatendimento.dao.AgendamentoDao;
import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.util.DataUtil;

@RestController
public class AgendamentoController {
	
	@Autowired
	private AgendamentoDao agendamentoDao;

	@PostMapping(value = "/realizaragendamento")
	public ResponseEntity<Boolean> realizarAgendamento(@RequestBody Agendamento agendamento){
		
		agendamento.setDataAgendamentoString(DataUtil.getDateFormatString(agendamento.getDataAgendamentoString(),"dd/MM/yyyy" ,"yyyyMMdd"));
		boolean statusAgendamento = agendamentoDao.inserir(agendamento);
		
		if (statusAgendamento) {
			return ResponseEntity.ok(true);
		}
		return ResponseEntity.ok(false);
	}
}
