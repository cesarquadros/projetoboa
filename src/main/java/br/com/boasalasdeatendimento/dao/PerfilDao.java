package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Perfil;

@Repository
public class PerfilDao extends ConexaoAzure {

	public Perfil getPerfil(Integer idPerfil) {

		final StringBuilder sql = new StringBuilder();

		try {
			conectar();

			sql.append(" SELECT * FROM");
			sql.append("	perfil ");
			sql.append(" WHERE ");
			sql.append("	idPerfil = ?");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idPerfil);

			rs = stmt.executeQuery();
			Perfil perfilAutenticado = new Perfil();

			while (rs.next()) {
				perfilAutenticado.setId(rs.getInt("idPerfil"));
				perfilAutenticado.setNomePerfil(rs.getString("nome_perfil"));
			}
			return perfilAutenticado;
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
			return null;
		}
	}
}
