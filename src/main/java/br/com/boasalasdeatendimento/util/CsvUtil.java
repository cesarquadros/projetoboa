package br.com.boasalasdeatendimento.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import br.com.boasalasdeatendimento.model.Agendamento;

@Component
public class CsvUtil {
	

	private static final String DIRETORIO = "./";
	private static final String EXTENSAO_CSV= ".csv";
	private static final String SEPARADOR = ",";
	private static final String QUEBRA_LINHA = "\n";
	private static final String RELATORIO = "Relatorio - ";

	public String gerarCsvOld(List<Agendamento> listaAgendamento) throws IOException {

		String dataAtual = DataUtil.getDataAtualSemSeparador();
		
		String arquivo = "Relatorio - "+ dataAtual + EXTENSAO_CSV;

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(DIRETORIO + arquivo));
				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("ID", "Cliente", "Data", "Horario", "Unidade", "Sala", "Status"));) {
			
			for (Agendamento agendamento : listaAgendamento) {
				
				csvPrinter.printRecord(agendamento.getId(), 
						agendamento.getCliente().getNome(), 
						agendamento.getDataAgendamentoString(),
						agendamento.getHorario().getHorarioString(),
						agendamento.getSala().getUnidade().getNomeUnidade(),
						"Sala" + agendamento.getSala().getNumero(),
						agendamento.getStatus());
			}

			csvPrinter.flush();
		}

		return arquivo;
	}
	
	public void gerarCsv(HttpServletResponse response, List<Agendamento> listaAgendamento) throws IOException {
		
		String dataAtual = DataUtil.getDataAtualSemSeparador();
		
		response.setContentType("text/csv");
		String arquivo = RELATORIO + dataAtual + EXTENSAO_CSV;
		response.setHeader("Content-disposition", "attachment;filename="+arquivo);
		
		ArrayList<String> rows = new ArrayList<>();
		rows.add("ID, CLIENTE, DATA, HORARIO, UNIDADE, SALA, STATUS");
		rows.add(QUEBRA_LINHA);
		
		for (Agendamento agendamento  : listaAgendamento) {
			rows.add(agendamento.getId()+ SEPARADOR + 
					agendamento.getCliente().getNome()+ SEPARADOR +
					agendamento.getDataAgendamentoString()+ SEPARADOR +
					agendamento.getHorario().getHorarioString()+ SEPARADOR +
					agendamento.getSala().getUnidade().getNomeUnidade()+ SEPARADOR +
					agendamento.getSala().getNumero()+ SEPARADOR +
					agendamento.getStatus());
			rows.add(QUEBRA_LINHA);
		}
		
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}
		response.getOutputStream().flush();
	}
}
