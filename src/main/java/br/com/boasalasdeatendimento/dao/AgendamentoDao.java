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
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.Horario;
import br.com.boasalasdeatendimento.model.RelatorioAgendamento;
import br.com.boasalasdeatendimento.model.Sala;
import br.com.boasalasdeatendimento.model.Unidade;
import br.com.boasalasdeatendimento.util.DataUtil;
import br.com.boasalasdeatendimento.util.Util;

@Repository
public class AgendamentoDao {

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
	
	public Boolean finalizarAgendamento(Integer idAgendamento) {

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

			stmt.setInt(aux++, 2);
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
	
	public Boolean finalizarAgendamentoByCliente(Integer idCliente, String data, String hora) {

		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;

		try {
			
			/*SQL SERVER*/ 
			sql.append(" UPDATE AGENDAMENTO ");
			sql.append(" SET ");
			sql.append(" 	status = ? ");
			sql.append(" FROM ");
			sql.append(" 	agendamento a");
			sql.append(" INNER JOIN ");
			sql.append(" 	horarios h ");
			sql.append(" ON ");
			sql.append(" 	a.id_horario = h.idHorario ");
			sql.append(" WHERE ");
			sql.append(" 	id_cliente = ? ");
			sql.append(" AND ");
			sql.append(" 	((dt_agendamento < ?) OR (dt_agendamento = ? AND h.horario < ? ))");
			sql.append(" AND ");
			sql.append(" 	a.status = ? ");
			
			/*MYSQL
			sql.append(" UPDATE AGENDAMENTO AS a  ");
			sql.append(" INNER JOIN ");
			sql.append(" 	horarios h ");
			sql.append(" ON ");
			sql.append(" 	a.id_horario = h.idHorario ");
			sql.append(" SET ");
			sql.append(" 	a.status = ? ");
			sql.append(" WHERE ");
			sql.append(" 	a.id_cliente = ? ");
			sql.append(" AND ");
			sql.append(" 	((a.dt_agendamento < ?) OR (a.dt_agendamento = ? AND h.horario < ? ))");
			sql.append(" AND ");
			sql.append(" 	a.status = ? ");
			*/
			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setInt(aux++, Util.FINALIZADO);
			stmt.setInt(aux++, idCliente);
			stmt.setString(aux++, DataUtil.getDateFormatString(data, "dd/MM/yyyy", "yyyy/MM/dd"));
			stmt.setString(aux++, DataUtil.getDateFormatString(data, "dd/MM/yyyy", "yyyy/MM/dd"));
			stmt.setString(aux++, hora);
			stmt.setInt(aux++, Util.ABERTO);
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}
	
	public Boolean finalizarAgendamento(String data, String hora) {

		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;

		try {
			
			/*SQL SERVER*/ 
			sql.append(" UPDATE AGENDAMENTO ");
			sql.append(" SET ");
			sql.append(" 	status = ? ");
			sql.append(" FROM ");
			sql.append(" 	agendamento a");
			sql.append(" INNER JOIN ");
			sql.append(" 	horarios h ");
			sql.append(" ON ");
			sql.append(" 	a.id_horario = h.idHorario ");
			sql.append(" WHERE ");
			sql.append(" 	((dt_agendamento < ?) OR (dt_agendamento = ? AND h.horario < ? ))");
			sql.append(" AND ");
			sql.append(" 	a.status = ? ");
			
			/*MYSQL
			sql.append(" UPDATE AGENDAMENTO AS a  ");
			sql.append(" INNER JOIN ");
			sql.append(" 	horarios h ");
			sql.append(" ON ");
			sql.append(" 	a.id_horario = h.idHorario ");
			sql.append(" SET ");
			sql.append(" 	a.status = ? ");
			sql.append(" WHERE ");
			sql.append(" 	((a.dt_agendamento < ?) OR (a.dt_agendamento = ? AND h.horario < ? ))");
			sql.append(" AND ");
			sql.append(" 	a.status = ? ");
			*/
			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setInt(aux++, Util.FINALIZADO);
			stmt.setString(aux++, DataUtil.getDateFormatString(data, "dd/MM/yyyy", "yyyy/MM/dd"));
			stmt.setString(aux++, DataUtil.getDateFormatString(data, "dd/MM/yyyy", "yyyy/MM/dd"));
			stmt.setString(aux++, hora);
			stmt.setInt(aux++, Util.ABERTO);
			
			stmt.execute();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}

	public boolean agendamentoExiste(Agendamento agendamento) {

		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			final StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT idAgendamento FROM");
			sql.append(" agendamento");
			sql.append(" WHERE");
			sql.append(" id_sala = ? and");
			sql.append(" id_horario = ? and");
			sql.append(" dt_agendamento = ? and");
			sql.append(" status = ? ");
			
			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setInt(aux++, agendamento.getSala().getId());
			stmt.setInt(aux++, agendamento.getHorario().getId());
			stmt.setString(aux++, DataUtil.getDateFormatString(agendamento.getDataAgendamentoString(), "dd/MM/yyyy", "yyyy-MM-dd"));
			stmt.setInt(aux++, 1);

			ResultSet rs = stmt.executeQuery();
			
			agendamento = new Agendamento();

			while (rs.next()) {
				return true;
			}
			
			return false;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
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
	
	public Agendamento findById(Integer idAgendamento) {

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
			sql.append(" 	a.idagendamento = ? ");
			sql.append(" ORDER BY ");
			sql.append(" 	a.dt_agendamento ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idAgendamento);

			ResultSet rs = stmt.executeQuery();

			Agendamento agendamento = null;

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

			}
			return agendamento;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
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
			sql.append(" ORDER BY ");
			sql.append(" 	a.dt_agendamento ");

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
	
	public List<Agendamento> relatorioAgendamento(RelatorioAgendamento relatorioAgendamento) {

		final StringBuilder sql = new StringBuilder();

		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT");
			sql.append("	 a.idagendamento, c.nome, c.sobrenome, a.dt_agendamento, h.horario, u.nome_unidade, s.numero, a.status ");
			sql.append(" FROM ");
			sql.append(" 	agendamento a ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	horarios h on a.id_horario = h.idhorario ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	sala s on a.id_sala = s.idsala ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	unidade u on s.id_unidade = u.idUnidade ");
			sql.append(" INNER JOIN	 ");
			sql.append(" 	cliente c on a.id_cliente = c.idCliente	 ");
			sql.append(" WHERE	 ");
			sql.append(" 	dt_agendamento between ? and  ? ");
			
			Boolean filtroStatus = false;
			
			if(relatorioAgendamento.getStatus() > 0) {
				sql.append(" and status = ?	 ");
				filtroStatus = true;
			}

			stmt = conexao.prepareStatement(sql.toString());

			Integer aux = 1;
			
			stmt.setString(aux++, DataUtil.getDateFormatString(relatorioAgendamento.getDataInicio(), "dd/MM/yyyy", "yyyy/MM/dd"));
			stmt.setString(aux++, DataUtil.getDateFormatString(relatorioAgendamento.getDataFim(), "dd/MM/yyyy", "yyyy/MM/dd"));
			
			if(filtroStatus) {
				stmt.setInt(aux++, relatorioAgendamento.getStatus());
			}

			ResultSet rs = stmt.executeQuery();

			Agendamento agendamento = new Agendamento();
			List<Agendamento> listaAgendamentos = new ArrayList<Agendamento>();

			while (rs.next()) {

				Horario horario = new Horario();
				Sala sala = new Sala();
				Unidade unidade = new Unidade();
				Cliente cliente = new Cliente();
				
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
				
				//setando cliente
				cliente.setNome(rs.getString("nome"));
				cliente.setSobrenome(rs.getString("sobrenome"));
				agendamento.setCliente(cliente);

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
