package br.com.boasalasdeatendimento.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdministrativoController {

	@RequestMapping("/administrativo")
	public static ModelAndView index() {
		
		ModelAndView modelAndView = new ModelAndView("indexadm");
		
		return modelAndView;
	}
	
	@RequestMapping("/relatorioagendamentos")
	public static ModelAndView relatorioAgendamentos() {

		ModelAndView modelAndView = new ModelAndView("relatorioagendamentos");

		return modelAndView;
	}
}
