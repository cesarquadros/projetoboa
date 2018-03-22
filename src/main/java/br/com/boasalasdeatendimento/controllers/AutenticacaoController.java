package br.com.boasalasdeatendimento.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.boasalasdeatendimento.dao.AgendamentoDao;
import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.dao.ClienteDao;
import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.security.GenerateHashPasswordUtil;
import br.com.boasalasdeatendimento.util.DataUtil;

@Controller
public class AutenticacaoController {

	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private AutenticarDao autenticarDao;
	
	@Autowired
	private AgendamentoDao agendamentoDao;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/autenticar")
	public ModelAndView autenticar(Autenticacao autenticacao, HttpSession session, RedirectAttributes redirectAttributes) {

		Autenticacao verificaAutenticacao = new Autenticacao();

		autenticacao.setSenha(GenerateHashPasswordUtil.generateHash(autenticacao.getSenha()));
		verificaAutenticacao = autenticarDao.autenticar(autenticacao);

		if (null != verificaAutenticacao.getId()) {

			Cliente cliente = clienteDao.findByIdAutenticacao(verificaAutenticacao);
			
			redirectAttributes.addFlashAttribute("cliente", cliente);
			redirectAttributes.addFlashAttribute("dataAtual", DataUtil.getDataAtual());
			
			session.setAttribute("usuarioLogado", cliente);
			
			finalizarAgendamentoByCliente(cliente.getId());
			
			if(verificaAutenticacao.getPerfil().getId() == 1) {
				
				return new ModelAndView("redirect:index");
			}
			
			return new ModelAndView("redirect:administrativo");
			
		} else {
			ModelAndView modelAndView = new ModelAndView("login");
			modelAndView.addObject("mensagemErro", "Usuário ou senha inválidos");
			return modelAndView; 
		}
	}
	
	
	@PostMapping(value = "/updatesenha")
	public ResponseEntity<?> atualizaSenha(@RequestBody Autenticacao autenticacao,	RedirectAttributes redirectAttributes, HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if (cliente != null) {
			
			Boolean verificaSenhaAtual = autenticarDao.findBySenha(autenticacao);
			
			if(verificaSenhaAtual) {
				
				Boolean alterarSenha = autenticarDao.updateSenha(autenticacao);
				
				if(alterarSenha) {
					return ResponseEntity.ok(null);
				} else {
					return new ResponseEntity<Error>(HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				return new ResponseEntity<Error>(HttpStatus.UNAUTHORIZED);
			}
		}
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}

	@RequestMapping("/logout")
	public ModelAndView logout(Autenticacao autenticacao, HttpSession session) {

		session.invalidate();

		return new ModelAndView("index");
	}
	
	public void finalizarAgendamentoByCliente(int idCliente) {
		
		String data = DataUtil.getDataAtual();
		String hora = DataUtil.getHoraAtual();
		agendamentoDao.finalizarAgendamentoByCLiente(idCliente, data, hora);
	}
	
}