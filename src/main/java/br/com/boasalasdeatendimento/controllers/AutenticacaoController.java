package br.com.boasalasdeatendimento.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AutenticacaoController {

	@RequestMapping("/login")
   public String login() {
	   System.out.println("redirecionando LOGIN");
      return "login";
   }

	@RequestMapping("/autenticar")
	public ModelAndView index() {
		 System.out.println("redirecionando INDEX via tela de login");
		 
		return HomeController.index();
	}
}
