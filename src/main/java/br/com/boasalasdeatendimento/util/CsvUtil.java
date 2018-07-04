package br.com.boasalasdeatendimento.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import br.com.boasalasdeatendimento.model.Agendamento;

public class CsvUtil {
	

	private static final String DIRETORIO = System.getProperty("user.dir");
	private static final String EXTENSAO_CSV= ".csv";

	public String gerarCsv(List<Agendamento> listaAgendamento) throws IOException {

		String arquivo = "Relatorio" + EXTENSAO_CSV;

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
}
