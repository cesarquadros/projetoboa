package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import br.com.boasalasdeatendimento.model.Autenticacao;

public class AutenticarDao extends ConexaoDao {

	public static void main(String[] args) {
		Autenticacao a = new Autenticacao();
		
		a.setUsuario("nino");
		a.setSenha("1907");
		
		AutenticarDao aDao = new AutenticarDao();
		
		Autenticacao aut = aDao.autenticar(a);
		
		System.out.println("");
	}
	
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

			stmt = (PreparedStatement) conexao.prepareStatement(sql.toString());
			
			int aux = 1;
			
			stmt.setString(aux ++, autenticacao.getUsuario());
			stmt.setString(aux ++, autenticacao.getSenha());
			
			rs = stmt.executeQuery();
			Autenticacao usuarioAutenticado = new Autenticacao();;
			
			PerfilDao perfilDao = new PerfilDao();
			
			while(rs.next()){
				
				usuarioAutenticado.setId(rs.getInt("idAutenticacao"));
				usuarioAutenticado.setUsuario(rs.getString("usuario"));
				usuarioAutenticado.setSenha(rs.getString("senha"));
				usuarioAutenticado.setPerfil(perfilDao.getPerfil(rs.getInt("id_perfil")));
				
			}
			
			return usuarioAutenticado;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
