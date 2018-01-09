package br.com.boasalasdeatendimento.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AgendamentoController {

	@RequestMapping("/meusagendamentos")
	public static ModelAndView index() {

		ModelAndView modelAndView = new ModelAndView("meusagendamentos");

		return modelAndView;
	}
}
