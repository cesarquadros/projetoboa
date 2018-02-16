package br.com.boasalasdeatendimento.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.dao.ClienteDao;
import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.security.GenerateHashPasswordUtil;
import br.com.boasalasdeatendimento.util.DataUtil;

@Controller
public class AutenticacaoController {

	@Autowired
	private ClienteDao ClienteDao;
	
	@RequestMapping("/login")
	public String login(Autenticacao autenticacao) {
		System.out.println("redirecionando LOGIN");
		return "login";
	}

	@RequestMapping("/autenticar")
	public String autenticar(Autenticacao autenticacao, HttpSession session, RedirectAttributes redirectAttributes) {

		AutenticarDao autenticarDao = new AutenticarDao();
		Autenticacao verificaAutenticacao = new Autenticacao();

		autenticacao.setSenha(GenerateHashPasswordUtil.generateHash(autenticacao.getSenha()));
		verificaAutenticacao = autenticarDao.autenticar(autenticacao);

		if (null != verificaAutenticacao.getId()) {

			Cliente cliente = ClienteDao.findByIdAutenticacao(verificaAutenticacao);
			
			redirectAttributes.addFlashAttribute("cliente", cliente);
			redirectAttributes.addFlashAttribute("dataAtual", DataUtil.getDateTime());
			
			session.setAttribute("usuarioLogado", cliente);

			return "redirect: index";
		} else {
			redirectAttributes.addFlashAttribute("erro", "Usuário ou senha inválidos");
		}
		return "redirect: login";
	}

	@RequestMapping("/logout")
	public ModelAndView logout(Autenticacao autenticacao, HttpSession session) {

		session.invalidate();

		return new ModelAndView("index");
	}
}