package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.PreparedStatement;

//mapeando o down
@Repository
@Transactional
public class ConexaoDao {
	
	private static String URL = "jdbc:mysql://localhost:3306/boa";
	private static String user = "root";
	private static String password = "admin";
	 private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	protected static Connection conexao;
	protected PreparedStatement stmt;
	protected ResultSet rs;
	
	public static Connection conectar() {
		
		try {
			
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL,user ,password );
			System.out.println("Conectado!");
			
			return conexao;
		} catch (SQLException e) {
			
			System.out.println("Erro ao conectar: "+ e.toString());
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
