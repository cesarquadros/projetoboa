package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Agendamento;

@Repository
public class AgendamentoDao extends ConexaoDao{

	public static void main(String[] args) {

		Agendamento a = new Agendamento();
		AgendamentoDao cDao = new AgendamentoDao();
		
		cDao.inserir(a);
	}
	
	public boolean inserir(Agendamento agendamento) {
		
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
			stmt.setBoolean(aux++, agendamento.getStatus());
			
			stmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
}
