package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.boasalasdeatendimento.model.Sala;

public class SalaDao {
	
	public List<Sala> listaSalaById(Integer idUnidade) {

		final StringBuilder sql = new StringBuilder();

		ConexaoDao c = new ConexaoDao();
		
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Sala ");
			sql.append(" WHERE ");
			sql.append(" 	id_unidade = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idUnidade);

			ResultSet rs = stmt.executeQuery();

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
		} finally {
			c.fecharConexao(stmt, conexao);
		}
	}
	
	public Sala listaSalaByIdComUnidade(Integer idSala) {

		final StringBuilder sql = new StringBuilder();

		ConexaoDao c = new ConexaoDao();
		
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;
		
		try {

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Sala ");
			sql.append(" WHERE ");
			sql.append(" 	idSala = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idSala);

			ResultSet rs = stmt.executeQuery();

			Sala sala = new Sala();;
			UnidadeDao unidadeDao = new UnidadeDao();

			while (rs.next()) {

				sala.setId(rs.getInt("idSala"));
				sala.setUnidade(unidadeDao.findByIdSemSalas(rs.getInt("id_unidade")));
				sala.setNumero(rs.getInt("numero"));
			}
			
			return sala;
		} catch (SQLException e) {
			e.printStackTrace();
			return null; 
		} finally {
			c.fecharConexao(stmt, conexao);
		}
	}
}
