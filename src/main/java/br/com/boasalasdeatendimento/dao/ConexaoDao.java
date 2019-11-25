package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

//mapeando o down
@Repository
public class ConexaoDao {
	
	private static String URL = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_86b6e8fc0bd3bb1?reconnect=true";
	private static String user = "b1cd525c86c3d2";
	private static String password = "1436d536";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	
	public Connection conectar() {
		
		try {
			Connection conexao;
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL,user ,password );
			
			return conexao;
		} catch (SQLException e) {
			System.out.println("Erro ao conectar: "+ e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void fecharConexao(PreparedStatement stmt, Connection conexao) {
		try {
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
