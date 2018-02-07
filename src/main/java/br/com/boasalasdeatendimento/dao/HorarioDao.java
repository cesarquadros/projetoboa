package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.boasalasdeatendimento.model.ConsultaSala;
import br.com.boasalasdeatendimento.model.Horario;

public class HorarioDao extends ConexaoDao {

	public List<Horario> horariosDisponiveis(ConsultaSala consultaSala) {
		try {

			final StringBuilder sql = new StringBuilder();

			conectar();

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
			sql.append(" 	a.id_sala = ? ) ");

			stmt = (PreparedStatement) conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setString(aux++, consultaSala.getData());
			stmt.setString(aux++, consultaSala.getSala());

			rs = stmt.executeQuery();

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
		}
	}
}
