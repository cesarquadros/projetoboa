package br.com.boasalasdeatendimento.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.dao.ClienteDao;
import br.com.boasalasdeatendimento.dao.UnidadeDao;
import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.ConsultaSala;
import br.com.boasalasdeatendimento.model.Status;
import br.com.boasalasdeatendimento.model.Unidade;
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

	@PostMapping(value = "/cadastrarcliente")
	public ModelAndView cadastrarCliente(@RequestBody Cliente cliente, Autenticacao autenticacao,
			RedirectAttributes redirectAttributes, HttpSession session) {

		List<String> listaErros = validatorCliente.validarCliente(cliente, autenticacao);

		ModelAndView modelAndView = new ModelAndView("cadastrocliente");

		if (listaErros.size() < 1) {

			autenticacao.setUsuario(cliente.getEmail());

			Boolean autenticacaoExiste = autenticarDao.findByUsuario(autenticacao.getUsuario());
			Boolean clienteExiste = clienteDao.findByIdCpf(cliente.getCpf());

			if (!autenticacaoExiste && !clienteExiste) {

				autenticacao = autenticarDao.inserir(autenticacao);

				cliente.setAutenticacao(autenticacao);
				Cliente clienteInserido = clienteDao.inserir(cliente);

				if (clienteInserido != null) {
					redirectAttributes.addFlashAttribute("cliente", cliente);
					session.setAttribute("usuarioLogado", cliente);
					return new ModelAndView("redirect:index");
				} else {
					modelAndView.addObject("mensagemErro", "Ocorreu um erro ao realizar o cadastro, tente novamente");
					modelAndView.addObject("cliente", cliente);
					return new ModelAndView("redirect:novocadastro");
				}
			} else {
				listaErros.add("CPF ou Email já cadastrados");
				modelAndView.addObject("usuarioJaCadastrado", listaErros);
				modelAndView.addObject("cliente", cliente);
				return modelAndView;
			}
		} else {
			modelAndView.addObject("listaErros", listaErros);
			modelAndView.addObject("cliente", cliente);
			return modelAndView;
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
}
