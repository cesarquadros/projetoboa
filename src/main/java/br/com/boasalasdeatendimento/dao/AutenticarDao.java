package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.security.GenerateHashPasswordUtil;

@Repository
public class AutenticarDao extends ConexaoDao {
	
	@Autowired
	private PerfilDao perfilDao;
	
	public Autenticacao autenticar(Autenticacao autenticacao) {

		final StringBuilder sql = new StringBuilder();

		try {
			conectar();

			sql.append(" SELECT * FROM");
			sql.append("	autenticacao ");
			sql.append(" WHERE ");
			sql.append("	usuario = ?");
			sql.append(" AND ");
			sql.append("	senha = ?");

			stmt = conexao.prepareStatement(sql.toString());

			int aux = 1;

			stmt.setString(aux++, autenticacao.getUsuario());
			stmt.setString(aux++, autenticacao.getSenha());

			rs = stmt.executeQuery();
			Autenticacao usuarioAutenticado = new Autenticacao();

			while (rs.next()) {

				usuarioAutenticado.setId(rs.getInt("idAutenticacao"));
				usuarioAutenticado.setUsuario(rs.getString("usuario"));
				usuarioAutenticado.setSenha(rs.getString("senha"));
				usuarioAutenticado.setPerfil(perfilDao.getPerfil(rs.getInt("id_perfil")));

			}

			fecharConexao();
			return usuarioAutenticado;
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
			return null;
		}
	}

	public Autenticacao inserir(Autenticacao autenticacao) {

		final StringBuilder sql = new StringBuilder();

		try {
			conectar();

			sql.append(" INSERT INTO ");
			sql.append(" 	autenticacao ");
			sql.append(" 	(id_perfil, usuario, senha) ");
			sql.append(" VALUES ");
			sql.append(" 	(?, ?, ?) ");

			stmt = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			int aux = 1;
			
			stmt.setInt(aux++, 1);
			stmt.setString(aux++, autenticacao.getUsuario());
			stmt.setString(aux++, GenerateHashPasswordUtil.generateHash(autenticacao.getSenha()));
			
			stmt.execute();
			
			rs = stmt.getGeneratedKeys();
			
			while (rs.next()) {
				
				 autenticacao.setId(rs.getInt(1));
				 autenticacao.setPerfil(perfilDao.getPerfil(1));

				 fecharConexao();
				 return autenticacao;
			}
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
		}
		return null;
	}

	public Boolean findByUsuario(String usuario) {
		
		final StringBuilder sql = new StringBuilder();
		
		conectar();
		
		try {
			
			sql.append(" SELECT * FROM");
			sql.append(" 	autenticacao");
			sql.append(" WHERE");
			sql.append(" 	usuario = ? ");
			
			stmt = conexao.prepareStatement(sql.toString());
			stmt.setString(1, usuario);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				fecharConexao();
				return true;
			}
			
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
		}
		
		return false;
	}
}
