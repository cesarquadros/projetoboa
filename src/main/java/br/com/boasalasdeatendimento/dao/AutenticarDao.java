package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;

import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.security.GenerateHashPasswordUtil;

@Repository
public class AutenticarDao {
	
	@Autowired
	private PerfilDao perfilDao;
	
	@Autowired
	private ConexaoDao conexaoDao;
	
	public Autenticacao autenticar(Autenticacao autenticacao) {

		final StringBuilder sql = new StringBuilder();

		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
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

			ResultSet rs = stmt.executeQuery();
			Autenticacao usuarioAutenticado = new Autenticacao();

			while (rs.next()) {

				usuarioAutenticado.setId(rs.getInt("idAutenticacao"));
				usuarioAutenticado.setUsuario(rs.getString("usuario"));
				usuarioAutenticado.setSenha(rs.getString("senha"));
			}

			return usuarioAutenticado;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
	}

	public Autenticacao inserir(Autenticacao autenticacao) {

		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;

		try {

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
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			while (rs.next()) {
				
				 autenticacao.setId(rs.getInt(1));
				 autenticacao.setPerfil(perfilDao.getPerfil(1));

				 return autenticacao;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}

	public Boolean findByUsuario(String usuario) {
		
		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT * FROM");
			sql.append(" 	autenticacao");
			sql.append(" WHERE");
			sql.append(" 	usuario = ? ");
			
			stmt = conexao.prepareStatement(sql.toString());
			stmt.setString(1, usuario);
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		
		return false;
	}
}
