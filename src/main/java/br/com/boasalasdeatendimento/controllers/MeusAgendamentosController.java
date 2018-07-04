package br.com.boasalasdeatendimento.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.boasalasdeatendimento.dao.AgendamentoDao;
import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.RelatorioAgendamento;
import br.com.boasalasdeatendimento.util.CsvUtil;
import br.com.boasalasdeatendimento.util.DataUtil;

@RestController
public class MeusAgendamentosController {

	private static final String DIRETORIO = System.getProperty("user.dir");
	
	@Autowired
	private AgendamentoDao agendamentoDao;

	@RequestMapping("/meusagendamentos")
	public static ModelAndView meusAgendamentos(HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

		if (cliente != null) {
			ModelAndView modelAndView = new ModelAndView("meusagendamentos");
			modelAndView.addObject("cliente", cliente);
			return modelAndView;
		}

		return new ModelAndView("redirect:login");
	}

	@RequestMapping("/meusagendamentos/{idCliente}")
	public ResponseEntity<?> getAgendamento(@PathVariable int idCliente, HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

		if (cliente != null && cliente.getId() == idCliente) {
			List<Agendamento> listaAgendamento = agendamentoDao.meusAgendamentosById(idCliente);
			return ResponseEntity.ok(listaAgendamento);
		}

		return new ResponseEntity<Error>(HttpStatus.CONFLICT);
	}

	@PostMapping(value = "/relatorioagendamentosadm")
	public ResponseEntity<?> relatorioAgendamento(@RequestBody RelatorioAgendamento relatorioAgendamento,
			HttpSession session) {

		if (relatorioAgendamento.getDataInicio().equals(null) || relatorioAgendamento.getDataFim().equals(null)
				|| relatorioAgendamento.getDataInicio().equals("") || relatorioAgendamento.getDataFim().equals("")) {

			return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);

		} else {

			Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

			if (cliente != null) {

				List<Agendamento> listaAgendamento = agendamentoDao.relatorioAgendamento(relatorioAgendamento);
				return ResponseEntity.ok(listaAgendamento);
			}
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping("/cancelaragendamento/{idAgendamento}")
	public ResponseEntity<?> cancelarAgendamento(@PathVariable int idAgendamento, HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

		Agendamento agendamento = agendamentoDao.findById(idAgendamento);

		Date timestampAgendamento = DataUtil.convertStringToDateTimeStamp(agendamento.getDataAgendamentoString(),
				agendamento.getHorario().getHorarioString());
		Date dataAtual = DataUtil.getTimestamp();

		String diferencaHoraString = DataUtil.getTimeDiff(dataAtual, timestampAgendamento);

		String arrayHora[] = diferencaHoraString.split(":");

		Integer diferencaHoras = Integer.parseInt(arrayHora[0]);

		if (diferencaHoras > 24) {
			if (cliente != null) {
				Boolean cancelarAgendamento = agendamentoDao.cancelarAgendamento(idAgendamento);

				if (cancelarAgendamento) {
					return new ResponseEntity<Error>(HttpStatus.OK);
				}
				return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Error>(HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping("/finalizaragendamento/{id}")
	public ResponseEntity<?> finalizarAgendamento(@PathVariable int id, HttpSession session) {

		Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");

		if (cliente != null) {
			Boolean cancelarAgendamento = agendamentoDao.finalizarAgendamento(id);

			if (cancelarAgendamento) {
				return new ResponseEntity<Error>(HttpStatus.OK);
			}
			return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/downloadcsv", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> download(@RequestBody List<Agendamento> listaAgendamento) throws IOException {
			
			CsvUtil csvUtil = new CsvUtil();
		
			String nomeArquivo = csvUtil.gerarCsv(listaAgendamento);
			
			byte[] arquivo = Files.readAllBytes(Paths.get(DIRETORIO + nomeArquivo));
			HttpHeaders httpHeaders = new HttpHeaders();
			
			httpHeaders.add("Content-Disposition", "attachment;filename=\"" + nomeArquivo + "\"");
			
			return ResponseEntity.ok(new HttpEntity<>(arquivo, httpHeaders));
			
		}

	/*
	 * @RequestMapping("/finalizaragendamentoscliente/{idCliente}") public
	 * ResponseEntity<?> finalizarAgendamentoByCliente(@PathVariable int idCliente,
	 * HttpSession session) {
	 * 
	 * Cliente cliente = (Cliente) session.getAttribute("usuarioLogado");
	 * 
	 * if (cliente != null) { String data = DataUtil.getDataAtual(); String hora =
	 * DataUtil.getHoraAtual(); Boolean cancelarAgendamento =
	 * agendamentoDao.finalizarAgendamentoByCLiente(idCliente, data, hora);
	 * 
	 * if (cancelarAgendamento) { return new ResponseEntity<Error>(HttpStatus.OK); }
	 * return new ResponseEntity<Error>(HttpStatus.BAD_REQUEST); } return new
	 * ResponseEntity<Error>(HttpStatus.BAD_REQUEST); }
	 */
}
