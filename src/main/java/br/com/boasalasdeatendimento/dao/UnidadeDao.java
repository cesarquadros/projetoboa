package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.boasalasdeatendimento.model.Unidade;

public class UnidadeDao {

	public List<Unidade> listaUnidades() {

		final StringBuilder sql = new StringBuilder();
		
		ConexaoDao c = new ConexaoDao();
	
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;

		try {

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Unidade ");

			stmt = conexao.prepareStatement(sql.toString());

			ResultSet rs = stmt.executeQuery();

			Unidade unindade;
			List<Unidade> listaUnidade = new ArrayList<Unidade>();
			SalaDao salaDao = new SalaDao();

			while (rs.next()) {
				unindade = new Unidade();
				unindade.setId(rs.getInt("idUnidade"));
				unindade.setNomeUnidade(rs.getString("nome_unidade"));
				unindade.setListaSala(salaDao.listaSalaById(unindade.getId()));
				listaUnidade.add(unindade);
			}
			return listaUnidade;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			c.fecharConexao(stmt, conexao);
		}
	}

	public Unidade findById(Integer idUnidade) {

		final StringBuilder sql = new StringBuilder();

		ConexaoDao c = new ConexaoDao();
		
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;
		
		try {

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Unidade ");
			sql.append(" WHERE ");
			sql.append(" 	idUnidade = ? ");

			int aux = 1;

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(aux++, idUnidade);

			ResultSet rs = stmt.executeQuery();

			Unidade unindade = new Unidade();

			SalaDao salaDao = new SalaDao();

			while (rs.next()) {

				unindade.setId(rs.getInt("idUnidade"));
				unindade.setNomeUnidade(rs.getString("nome_unidade"));
				unindade.setListaSala(salaDao.listaSalaById(idUnidade));
			}
			return unindade;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			c.fecharConexao(stmt, conexao);
		}
		return null;
	}

	public Unidade findByIdSemSalas(Integer idUnidade) {

		final StringBuilder sql = new StringBuilder();

		ConexaoDao c = new ConexaoDao();
		
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;
		
		try {
			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Unidade ");
			sql.append(" WHERE ");
			sql.append(" 	idUnidade = ? ");

			int aux = 1;

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(aux++, idUnidade);

			ResultSet rs = stmt.executeQuery();

			Unidade unindade = new Unidade();

			while (rs.next()) {

				unindade.setId(rs.getInt("idUnidade"));
				unindade.setNomeUnidade(rs.getString("nome_unidade"));
				return unindade;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			c.fecharConexao(stmt, conexao);
		}
		return null;
	}
}
