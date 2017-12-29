package br.com.boasalasdeatendimento.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/login")
   public String login() {
	   System.out.println("redirecionando LOGIN");
      return "login";
   }

	@RequestMapping("/")
	public String index() {
		 System.out.println("redirecionando INDEX");
		return "index";
	}
}
