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
import br.com.boasalasdeatendimento.util.DataUtil;
import br.com.boasalasdeatendimento.validators.ValidaCPF;
import br.com.boasalasdeatendimento.validators.ValidatorCliente;

@RestController
public class ClienteController {

	@Autowired
	private AutenticarDao autenticarDao;

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private ValidatorCliente validatorCliente;

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
	public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente, Autenticacao autenticacao,
			RedirectAttributes redirectAttributes, HttpSession session) {

		List<String> listaErros = validatorCliente.validarCliente(cliente);

		if (!ValidaCPF.isCPF(cliente.getCpf().replace("-", "").replace(".", ""))) {
			listaErros.add("CPF digitado inválido");
			return new ResponseEntity<List<String>>(listaErros, HttpStatus.EXPECTATION_FAILED);
		}

		if (listaErros.size() < 1) {

			autenticacao.setUsuario(cliente.getEmail());

			Boolean autenticacaoExiste = autenticarDao.findByUsuario(autenticacao.getUsuario());
			Boolean clienteExiste = clienteDao.findByIdCpf(cliente.getCpf());

			if (!autenticacaoExiste && !clienteExiste) {

				autenticacao = autenticarDao.inserir(autenticacao);

				cliente.setAutenticacao(autenticacao);
				Cliente clienteInserido = clienteDao.inserir(cliente);

				if (clienteInserido != null) {
					session.setAttribute("usuarioLogado", cliente);
					return ResponseEntity.ok(null);
				} else {
					listaErros.add("Erro ao realizar cadastro, tente novamente");
					return new ResponseEntity<List<String>>(listaErros, HttpStatus.BAD_REQUEST);
				}
			} else {
				listaErros.add("CPF ou Email já cadastrados");
				return new ResponseEntity<List<String>>(listaErros, HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<List<String>>(listaErros, HttpStatus.BAD_GATEWAY);
		}
	}
	
	@RequestMapping("/novocadastro")
	public static ModelAndView formCadastro(HttpSession session) {

		ModelAndView modelAndView = new ModelAndView("cadastrocliente");
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		modelAndView.addObject("cliente", cliente);

		return modelAndView;
	}

	@PostMapping(value = "/relatorioclientes")
	public ResponseEntity<?> carregarSalas(HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		List<Cliente> listaCliente = new ArrayList<Cliente>();

		if (cliente != null) {
			if (cliente.getAutenticacao().getPerfil().getId() == 2) {
				listaCliente = clienteDao.clienteListAll();
				return ResponseEntity.ok(listaCliente);
			}
		}
		return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/cadacli")
	public void carregarSalas() {

		System.out.println("");
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
			Cliente clienteById = clienteDao.findByIdCpf(id);

			if (clienteById != null) {
				return ResponseEntity.ok(clienteById);
			}
			return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
	}
}
