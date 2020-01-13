package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.dao.ClienteDao;
import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.service.ClienteService;
import br.com.boasalasdeatendimento.util.DataUtil;
import br.com.boasalasdeatendimento.validators.ValidatorCliente;

@RestController
public class ClienteController {

	@Autowired
	private AutenticarDao autenticarDao;

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private ValidatorCliente validatorCliente;
	
	@Autowired
	private ClienteService clienteService;

	/*
	 * @PostMapping(value = "/cadastrarcliente") public ModelAndView
	 * cadastrarCliente(@RequestBody Cliente cliente, Autenticacao autenticacao,
	 * RedirectAttributes redirectAttributes, HttpSession session) {
	 * 
	 * List<String> listaErros = validatorCliente.validarCliente(cliente);
	 * 
	 * ModelAndView modelAndView = new ModelAndView("cadastrocliente");
	 * 
	 * if (listaErros.size() < 1) {
	 * 
	 * autenticacao.setUsuario(cliente.getEmail());
	 * 
	 * Boolean autenticacaoExiste =
	 * autenticarDao.findByUsuario(autenticacao.getUsuario()); Boolean clienteExiste
	 * = clienteDao.findByIdCpf(cliente.getCpf());
	 * 
	 * if (!autenticacaoExiste && !clienteExiste) {
	 * 
	 * autenticacao = autenticarDao.inserir(autenticacao);
	 * 
	 * cliente.setAutenticacao(autenticacao); Cliente clienteInserido =
	 * clienteDao.inserir(cliente);
	 * 
	 * if (clienteInserido != null) {
	 * redirectAttributes.addFlashAttribute("cliente", cliente);
	 * session.setAttribute("usuarioLogado", cliente); return new
	 * ModelAndView("redirect:index"); } else {
	 * modelAndView.addObject("mensagemErro",
	 * "Ocorreu um erro ao realizar o cadastro, tente novamente");
	 * modelAndView.addObject("cliente", cliente); return new
	 * ModelAndView("redirect:novocadastro"); } } else {
	 * listaErros.add("CPF ou Email já cadastrados");
	 * modelAndView.addObject("usuarioJaCadastrado", listaErros);
	 * modelAndView.addObject("cliente", cliente); return modelAndView; } } else {
	 * modelAndView.addObject("listaErros", listaErros);
	 * modelAndView.addObject("cliente", cliente); return modelAndView; } }
	 */

	@PostMapping(value = "/cadastrarcliente")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente, Autenticacao autenticacao, RedirectAttributes redirectAttributes, HttpSession session) {
		return clienteService.salvarCliente(cliente, autenticacao, session);
	}
	
	@RequestMapping("/novocadastro")
	public static ModelAndView formCadastro(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView("cadastrocliente");
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		modelAndView.addObject("cliente", cliente);
		return modelAndView;
	}

	@PostMapping(value = "/relatorioclientes")
	public ResponseEntity<?> carregarClientes(HttpSession session) {
		return clienteService.listarTodosClientes(session);
	}

	@RequestMapping("/meuperfil")
	public static ModelAndView meuPerfil(HttpSession session) {
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		ModelAndView modelAndView;
		if (cliente != null) {
			modelAndView = new ModelAndView("meuperfil");
			modelAndView.addObject("dataAtual", DataUtil.getDataAtual());
			modelAndView.addObject("cliente", cliente);
			return modelAndView;
		}
		modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	@RequestMapping("/getcliente/{id}")
	public ResponseEntity<?> finalizarAgendamento(@PathVariable int id, HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

		if (cliente != null) {
			Cliente clienteById = clienteDao.findById(id);

			if (clienteById != null) {
				return ResponseEntity.ok(clienteById);
			}
			return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value = "/updatecadastro")
	public ResponseEntity<?> atualizaSenha(@RequestBody Cliente clienteUpdate,	RedirectAttributes redirectAttributes, HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		
		if (cliente != null) {
			
			Boolean validaIgualdadeCpf = validaIgualdadeCpf(cliente, clienteUpdate);
			
			if(validaIgualdadeCpf) {
				
				Boolean updateCliente = clienteDao.updateCliente(clienteUpdate);
				
				if(updateCliente) {
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
	
	public boolean validaIgualdadeCpf(Cliente cliente, Cliente clienteUpdate) {

		if(cliente.getCpf().equals(null) || cliente.getCpf().equals("") || !cliente.getCpf().equals(clienteUpdate.getCpf())) {
			return false;
		}
		
		return true;
	}
}
