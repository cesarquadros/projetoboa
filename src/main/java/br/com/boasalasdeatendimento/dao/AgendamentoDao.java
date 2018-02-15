package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.model.Cliente;

@Repository
public class AgendamentoDao extends ConexaoDao{

	public static void main(String[] args) {

		Agendamento a = new Agendamento();
		AgendamentoDao cDao = new AgendamentoDao();
		
		cDao.inserir(a);
	}
	
	public void inserir(Agendamento agendamento) {
		
		try {
			final StringBuilder sql = new StringBuilder();

			conectar();
			
			sql.append(" INSERT INTO ");
			sql.append(" 	agendamento ");
			sql.append(" 	(id_horario, id_cliente, id_sala, dt_agendamento, status) ");
			sql.append(" VALUES ");
			sql.append(" 	(?, ?, ?, ?, ?) ");
			
			stmt = conexao.prepareStatement(sql.toString());
			
			int aux =1;
			
			stmt.setInt(aux++, agendamento.getHorario().getId());
			stmt.setInt(aux++, agendamento.getCliente().getId());
			stmt.setInt(aux++, agendamento.getSala().getId());
			stmt.setString(aux++, agendamento.getDataAgendamentoString());
			stmt.setInt(aux++, 1);
			
			stmt.execute();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
