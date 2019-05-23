package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Perfil;

@Repository
public class PerfilDao {
	
	@Autowired
	private ConexaoDao conexaoDao;

	public Perfil getPerfil(Integer idPerfil) {

		final StringBuilder sql = new StringBuilder();

		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			sql.append(" SELECT * FROM");
			sql.append("	perfil ");
			sql.append(" WHERE ");
			sql.append("	idPerfil = ?");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idPerfil);

			ResultSet rs = stmt.executeQuery();
			Perfil perfilAutenticado = new Perfil();

			while (rs.next()) {
				perfilAutenticado.setId(rs.getInt("idPerfil"));
				perfilAutenticado.setNomePerfil(rs.getString("nome_perfil"));
			}
			return perfilAutenticado;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
	}
}
