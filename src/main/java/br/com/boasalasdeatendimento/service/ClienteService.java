package br.com.boasalasdeatendimento.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.boasalasdeatendimento.dao.AutenticarDao;
import br.com.boasalasdeatendimento.dao.ClienteDao;
import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.validators.ValidatorCliente;

@Service
public class ClienteService {
	
	@Autowired
	private ValidatorCliente validatorCliente;
	
	@Autowired
	private AutenticarDao autenticarDao;
	
	@Autowired
	private ClienteDao clienteDao;

	public ResponseEntity<List<String>> salvarCliente(Cliente cliente, Autenticacao autenticacao, HttpSession session){
		List<String> listaErros = validatorCliente.validarCliente(cliente);
		/*
		if (!ValidaCPF.isCPF(cliente.getCpf().replace("-", "").replace(".", ""))) {
			listaErros.add("CPF digitado inválido");
			return new ResponseEntity<List<String>>(listaErros, HttpStatus.EXPECTATION_FAILED);
		}
		*/
		if (listaErros.isEmpty()) {
			autenticacao.setUsuario(cliente.getEmail());
			autenticacao.setSenha(cliente.getAutenticacao().getSenha());

			boolean autenticacaoExiste = autenticarDao.findByUsuario(autenticacao.getUsuario());
			boolean clienteExiste = clienteDao.findByCpf(cliente.getCpf());

			if (!autenticacaoExiste && !clienteExiste) {

				autenticacao = autenticarDao.inserir(autenticacao);

				cliente.setAutenticacao(autenticacao);
				Cliente clienteInserido = clienteDao.inserir(cliente);

				if (clienteInserido != null) {
					session.setAttribute("usuarioLogado", cliente);
					return ResponseEntity.ok(null);
				} else {
					listaErros.add("Erro ao realizar cadastro, tente novamente");
					return new ResponseEntity<>(listaErros, HttpStatus.BAD_REQUEST);
				}
			} else {
				listaErros.add("CPF ou Email já cadastrados");
				return new ResponseEntity<>(listaErros, HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<>(listaErros, HttpStatus.BAD_GATEWAY);
		}
	}
	
	public ResponseEntity<?> listarTodosClientes(HttpSession session){
		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		if (cliente != null) {
			if (cliente.getAutenticacao().getPerfil().getId() == 2) {
				listaCliente = clienteDao.clienteListAll();
				return ResponseEntity.ok(listaCliente);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
