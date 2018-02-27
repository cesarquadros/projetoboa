package br.com.boasalasdeatendimento.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.boasalasdeatendimento.dao.AgendamentoDao;
import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.Unidade;

@RestController
public class MeusAgendamentosController {
	
	@Autowired
	private AgendamentoDao agendamentoDao;
	
	@Autowired
	private Agendamento agendamento;

	@RequestMapping("/meusagendamentos")
	public static ModelAndView meusAgendamentos(HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

		if (cliente != null) {
			ModelAndView modelAndView = new ModelAndView("meusagendamentos");
			modelAndView.addObject("cliente", cliente);
			return modelAndView;
		}

		return new ModelAndView("redirect: login");
	}

	@RequestMapping("/meusagendamentos/{idCliente}")
	public ResponseEntity<?> getAgendamento(@PathVariable int idCliente, HttpSession session){// REST Endpoint.

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if(cliente != null && cliente.getId() == idCliente) {
			List<Agendamento> listaAgendamento = agendamentoDao.meusAgendamentosById(idCliente);
			return ResponseEntity.ok(listaAgendamento);
		}
		
		return new ResponseEntity<Error>(HttpStatus.CONFLICT);
	}
}
