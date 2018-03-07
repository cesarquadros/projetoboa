package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.boasalasdeatendimento.model.Autenticacao;

public class ConexaoAzure {
	
	public static void main(String[] args) {
		
		conectar();
		
		Autenticacao autenticacao = new Autenticacao();
		
		autenticacao.setUsuario("teste");
		autenticacao.setSenha("teste");
		
		autenticar(autenticacao);
		
	}
	
	public static Autenticacao autenticar(Autenticacao autenticacao) {

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

			}

			fecharConexao();
			return usuarioAutenticado;
		} catch (SQLException e) {
			fecharConexao();
			e.printStackTrace();
			return null;
		}
	}
	
	private static String URL = "jdbc:sqlserver://boaatendimento.database.windows.net:1433;database=boa;user=boa@boaatendimento;password=Ces@r190788;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
	// Esse � o nome do driver, que na internet voc� vai encontrar de varias
	// maneiras, mas s� esse resolveu meus problemas
	private static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	protected static Connection conexao;
	protected static PreparedStatement stmt;
	protected static ResultSet rs;

	public static Connection conectar()  {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL);
			System.out.println("Conectado");
			return con;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return conexao = null;

		}
	}
	
	public static void fecharConexao() {
		try {
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}


