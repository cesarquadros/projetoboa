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
import br.com.boasalasdeatendimento.model.Horario;
import br.com.boasalasdeatendimento.model.Sala;
import br.com.boasalasdeatendimento.model.Unidade;
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
			
			sql.append(" SELECT");
			sql.append("	 a.idagendamento, a.dt_agendamento, h.horario, u.nome_unidade, s.numero, a.status ");
			sql.append(" FROM ");
			sql.append(" 	agendamento a ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	horarios h on a.id_horario = h.idhorario ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	sala s on a.id_sala = s.idsala ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	unidade u on s.id_unidade = u.idUnidade ");
			sql.append(" WHERE	 ");
			sql.append(" 	a.id_cliente = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idCliente);

			ResultSet rs = stmt.executeQuery();

			Agendamento agendamento = new Agendamento();
			List<Agendamento> listaAgendamentos = new ArrayList<Agendamento>();

			while (rs.next()) {

				Horario horario = new Horario();
				Sala sala = new Sala();
				Unidade unidade = new Unidade();
				
				agendamento = new Agendamento();

				agendamento.setId(rs.getInt("idAgendamento"));
				agendamento.setDataAgendamentoString(DataUtil.getDateFormatString(rs.getString("dt_agendamento"), "yyyy-MM-dd", "dd/MM/yyyy"));
				agendamento.setStatus(Util.mapStatus.get(rs.getInt("status")));
				
				//setando horario
				horario.setHorarioString(rs.getString("horario"));
				agendamento.setHorario(horario);
				
				//setando numero da sala e nome unidade
				sala.setNumero(rs.getInt("numero"));
				unidade.setNomeUnidade(rs.getString("nome_unidade"));
				sala.setUnidade(unidade);
				agendamento.setSala(sala);

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
