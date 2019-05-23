package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.boasalasdeatendimento.dao.HorarioDao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.ConsultaSala;
import br.com.boasalasdeatendimento.model.Horario;
import br.com.boasalasdeatendimento.util.DataUtil;

@RestController
public class HorarioRestController {
	
	@Autowired
	private HorarioDao horarioDao;

	@PostMapping(value = "/carregarhorariodisponivel")
	public ResponseEntity<List<Horario>> testeGet(@RequestBody ConsultaSala consultaSala, HttpSession session, RedirectAttributes redirectAttributes) {
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if(cliente != null) {
			
			if(DataUtil.stringToDate(consultaSala.getData()).before(DataUtil.getDataAtualDate())) {
				return ResponseEntity.ok(null);
			} else {
			
			List<Horario> listaHorario = new ArrayList<Horario>();
	
			listaHorario = horarioDao.horariosDisponiveis(consultaSala);
	
			redirectAttributes.addFlashAttribute("clieente", cliente);
			
			return ResponseEntity.ok(listaHorario);
			}
		} else {
			return ResponseEntity.ok(null);
		}
	}
	
}
