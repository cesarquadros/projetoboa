package br.com.boasalasdeatendimento.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.security.GenerateHashPasswordUtil;
import br.com.boasalasdeatendimento.util.DataUtil;

@Controller
public class AutenticacaoController {

	@RequestMapping("/login")
   public String login(Autenticacao autenticacao) {
	   System.out.println("redirecionando LOGIN");
      return "login";
   }

	@RequestMapping("/autenticar")
	public ModelAndView autenticar(Autenticacao autenticacao, HttpSession session) {
		
		ModelAndView modelAndView = null;
		
		AutenticarDao autenticarDao = new AutenticarDao();
		Autenticacao verificaAutenticacao = new Autenticacao();
		
		autenticacao.setSenha(GenerateHashPasswordUtil.generateHash(autenticacao.getSenha()));
		verificaAutenticacao = autenticarDao.autenticar(autenticacao);
		
		if( null != verificaAutenticacao.getId()){
			
			modelAndView = new ModelAndView("index");
			modelAndView.addObject("dataAtual", DataUtil.getDateTime());
			
			Cliente cliente = new Cliente();
			
			cliente.setId(1);
			cliente.setNome("Cesar");
			session.setAttribute("cliente", cliente);
			modelAndView.addObject("cliente", cliente);
			return modelAndView;
		} else {
			modelAndView = new ModelAndView("login");
			modelAndView.addObject("erro", "Usuario ou senha inválidos");
		}
		return modelAndView;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(Autenticacao autenticacao, HttpSession session) {
		
		session.invalidate();
		
		return HomeController.index();
	}
}