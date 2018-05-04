package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

//mapeando o down
@Repository
public class ConexaoDao1 {
	
	private static String URL = "jdbc:mysql://localhost:3306/boa";
	private static String user = "root";
	private static String password = "admin";
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
