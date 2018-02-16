package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.Horario;
import br.com.boasalasdeatendimento.util.DataUtil;

@Controller
public class HomeController {

	
	@RequestMapping("/")
	public static ModelAndView index(HttpSession session) {
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("dataAtual", DataUtil.getDateTime());
		modelAndView.addObject("cliente", cliente);
		
		return modelAndView;
	}
	
	@RequestMapping("/index")
	public static ModelAndView index2( HttpSession session) {
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("dataAtual", DataUtil.getDateTime());
		modelAndView.addObject("cliente", cliente);
		return modelAndView;
	}
}
