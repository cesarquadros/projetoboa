package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import br.com.boasalasdeatendimento.model.Unidade;

public class UnidadeDao extends ConexaoDao {

	public List<Unidade> listaUnidades() {

		final StringBuilder sql = new StringBuilder();

		try {
			conectar();

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	Unidade ");

			stmt = conexao.prepareStatement(sql.toString());

			rs = stmt.executeQuery();

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
			fecharConexao();
			return listaUnidade;
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
			return null;
		}
	}

	public Unidade findById(Integer idUnidade) throws SQLException, ClassNotFoundException {

		final StringBuilder sql = new StringBuilder();

		conectar();

		sql.append(" SELECT * ");
		sql.append(" FROM ");
		sql.append(" 	Unidade ");
		sql.append(" WHERE ");
		sql.append(" 	idUnidade = ? ");

		int aux = 1;

		stmt = conexao.prepareStatement(sql.toString());

		stmt.setInt(aux++, idUnidade);

		rs = stmt.executeQuery();

		Unidade unindade = new Unidade();

		SalaDao salaDao = new SalaDao();

		while (rs.next()) {

			unindade.setId(rs.getInt("idUnidade"));
			unindade.setNomeUnidade(rs.getString("nome_unidade"));
			unindade.setListaSala(salaDao.listaSalaById(idUnidade));
		}
		fecharConexao();
		return unindade;
	}
	
	public Unidade findByIdSemSalas(Integer idUnidade) {

		final StringBuilder sql = new StringBuilder();

		try {
		conectar();

		sql.append(" SELECT * ");
		sql.append(" FROM ");
		sql.append(" 	Unidade ");
		sql.append(" WHERE ");
		sql.append(" 	idUnidade = ? ");

		int aux = 1;

		stmt = conexao.prepareStatement(sql.toString());

		stmt.setInt(aux++, idUnidade);

		rs = stmt.executeQuery();

		Unidade unindade = new Unidade();

		while (rs.next()) {

			unindade.setId(rs.getInt("idUnidade"));
			unindade.setNomeUnidade(rs.getString("nome_unidade"));
			return unindade;
		}

		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
		}
		return null;
	}
}
