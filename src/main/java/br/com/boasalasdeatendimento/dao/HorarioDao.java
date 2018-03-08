package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.ConsultaSala;
import br.com.boasalasdeatendimento.model.Horario;

@Repository
public class HorarioDao {


	public List<Horario> horariosDisponiveis(ConsultaSala consultaSala) {

		final StringBuilder sql = new StringBuilder();

		ConexaoDao c = new ConexaoDao();
		
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;

		try {

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	horarios h ");
			sql.append(" WHERE NOT EXISTS ");
			sql.append(" (SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	agendamento a ");
			sql.append(" WHERE ");
			sql.append(" 	h.idHorario = a.id_horario ");
			sql.append(" AND ");
			sql.append(" 	a.dt_agendamento = ? ");
			sql.append(" AND  ");
			sql.append(" 	a.id_sala = ? ");
			sql.append(" AND  ");
			sql.append(" 	a.status = ? ) ");

			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setString(aux++, consultaSala.getData());
			stmt.setString(aux++, consultaSala.getSala());
			stmt.setInt(aux++, 1);

			ResultSet rs = stmt.executeQuery();

			List<Horario> listaHorarios = new ArrayList<Horario>();

			Horario horario;

			while (rs.next()) {
				horario = new Horario();

				horario.setId(rs.getInt("idHorario"));
				horario.setHorarioString(rs.getString("horario"));

				listaHorarios.add(horario);
			}
			return listaHorarios;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			c.fecharConexao(stmt, conexao);
		}
	}

	public Horario findHorarioById(Integer idHorario) {

		final StringBuilder sql = new StringBuilder();
		
		ConexaoDao c = new ConexaoDao();

		Connection conexao = c.conectar();
		PreparedStatement stmt = null;

		try {

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	horarios ");
			sql.append(" WHERE ");
			sql.append(" 	idHorario = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setInt(aux++, idHorario);

			ResultSet rs = stmt.executeQuery();

			Horario horario = null;

			while (rs.next()) {

				horario = new Horario();
				horario.setId(rs.getInt("idHorario"));
				horario.setHorarioString(rs.getString("horario"));
			}
			return horario;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			c.fecharConexao(stmt, conexao);
		}
	}
}
