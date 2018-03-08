package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.util.DataUtil;
import br.com.boasalasdeatendimento.util.Util;

@Repository
public class AgendamentoDao {

	public static void main(String[] args) {

		Agendamento a = new Agendamento();
		AgendamentoDao cDao = new AgendamentoDao();

		cDao.inserir(a);
	}

	@Autowired
	private ConexaoDao conexaoDao;
	
	public Boolean cancelarAgendamento(Integer idAgendamento) {

		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;

		try {

			sql.append(" UPDATE AGENDAMENTO ");
			sql.append(" SET ");
			sql.append(" 	status = ? ");
			sql.append(" WHERE ");
			sql.append(" 	idAgendamento = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setInt(aux++, 3);
			stmt.setInt(aux++, idAgendamento);
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}

	public boolean inserir(Agendamento agendamento) {

		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			final StringBuilder sql = new StringBuilder();

			sql.append(" INSERT INTO ");
			sql.append(" 	agendamento ");
			sql.append(" 	(id_horario, id_cliente, id_sala, dt_agendamento) ");
			sql.append(" VALUES ");
			sql.append(" 	(?, ?, ?, ?) ");

			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setInt(aux++, agendamento.getHorario().getId());
			stmt.setInt(aux++, agendamento.getCliente().getId());
			stmt.setInt(aux++, agendamento.getSala().getId());
			stmt.setString(aux++, agendamento.getDataAgendamentoString());

			stmt.execute();
			
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
	}

	public List<Agendamento> meusAgendamentosById(Integer idCliente) {

		final StringBuilder sql = new StringBuilder();

		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT * FROM");
			sql.append("	agendamento ");
			sql.append(" WHERE ");
			sql.append(" 	id_cliente = ?");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idCliente);

			ResultSet rs = stmt.executeQuery();

			Agendamento agendamento = new Agendamento();
			List<Agendamento> listaAgendamentos = new ArrayList<Agendamento>();

			HorarioDao horarioDao = new HorarioDao();
			SalaDao salaDao = new SalaDao();

			while (rs.next()) {

				agendamento = new Agendamento();

				agendamento.setId(rs.getInt("idAgendamento"));
				agendamento.setStatus(Util.mapStatus.get(rs.getInt("status")));
				agendamento.setHorario(horarioDao.findHorarioById(rs.getInt("id_horario")));
				agendamento.setSala(salaDao.listaSalaByIdComUnidade(rs.getInt("id_sala")));
				agendamento.setDataAgendamentoString(DataUtil.getDateFormatString(rs.getString("dt_agendamento"), "yyyy-MM-dd", "dd/MM/yyyy"));

				listaAgendamentos.add(agendamento);

			}
			return listaAgendamentos;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}

}
