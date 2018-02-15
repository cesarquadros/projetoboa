package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.chainsaw.Main;

import com.mysql.jdbc.PreparedStatement;

import br.com.boasalasdeatendimento.model.Sala;
import br.com.boasalasdeatendimento.model.Unidade;

public class SalaDao extends ConexaoDao {

	public List<Sala> listaSalaById(Integer idUnidade) {

		final StringBuilder sql = new StringBuilder();

		try {
			conectar();

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Sala ");
			sql.append(" WHERE ");
			sql.append(" 	id_unidade = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idUnidade);

			rs = stmt.executeQuery();

			Sala sala;
			List<Sala> listaSala = new ArrayList<Sala>();

			while (rs.next()) {

				sala = new Sala();
				sala.setId(rs.getInt("idSala"));
				sala.setIdUnidade(rs.getInt("id_unidade"));
				sala.setNumero(rs.getInt("numero"));
				listaSala.add(sala);
			}
			
			return listaSala;
		} catch (SQLException e) {
			e.printStackTrace();
			return null; 
		} 
	}
}
