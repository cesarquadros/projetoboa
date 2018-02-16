package br.com.boasalasdeatendimento.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.boasalasdeatendimento.dao.AgendamentoDao;
import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.util.DataUtil;

@RestController
public class AgendamentoController {
	
	@Autowired
	private AgendamentoDao agendamentoDao;

	@RequestMapping("/meusagendamentos")
	public static ModelAndView meusAgendamentos(HttpSession session) {
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if(cliente != null) {
			ModelAndView modelAndView = new ModelAndView("meusagendamentos");
			modelAndView.addObject("cliente", cliente);
			return modelAndView;
		}

		return new ModelAndView("login");
	}
	
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
