package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.boasalasdeatendimento.model.Sala;

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
			fecharConexao();
			e.printStackTrace();
			return null; 
		} 
	}
	
	public Sala listaSalaByIdComUnidade(Integer idSala) {

		final StringBuilder sql = new StringBuilder();

		try {
			conectar();

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Sala ");
			sql.append(" WHERE ");
			sql.append(" 	idSala = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idSala);

			rs = stmt.executeQuery();

			Sala sala = new Sala();;
			UnidadeDao unidadeDao = new UnidadeDao();

			while (rs.next()) {

				sala.setId(rs.getInt("idSala"));
				sala.setUnidade(unidadeDao.findByIdSemSalas(rs.getInt("id_unidade")));
				sala.setNumero(rs.getInt("numero"));
			}
			
			return sala;
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
			return null; 
		} 
	}
}
