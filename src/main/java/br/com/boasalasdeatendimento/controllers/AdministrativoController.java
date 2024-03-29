package br.com.boasalasdeatendimento.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.util.DataUtil;

@Controller
public class AdministrativoController {

	@RequestMapping("/administrativo")
	public static ModelAndView index(HttpSession session) {
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if (cliente != null) {
			if(cliente.getAutenticacao().getPerfil().getId() == 2) {
				
				ModelAndView modelAndView = new ModelAndView("indexadm");
				modelAndView.addObject("dataAtual", DataUtil.getDataAtual());
				
				return modelAndView;
			} else {
				return new ModelAndView("redirect:index");
			}
		} else {
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping("/relatorioagendamentos")
	public static ModelAndView relatorioAgendamentos(HttpSession session) {
		
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if (cliente != null) {
			if(cliente.getAutenticacao().getPerfil().getId() == 2) {
				return new ModelAndView("relatorioagendamentos");
			} else {
				return new ModelAndView("redirect:index");
			}
		} else {
			return new ModelAndView("redirect:login");
		}
	}
}
