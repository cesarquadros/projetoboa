package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import br.com.boasalasdeatendimento.model.Autenticacao;

@Component
public class ConexaoDao2 {
	
	private static String URL = "jdbc:sqlserver://boaatendimento.database.windows.net:1433;database=boa;user=boa@boaatendimento;password=Ces@r190788;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30";
	// Esse � o nome do driver, que na internet voc� vai encontrar de varias
	// maneiras, mas s� esse resolveu meus problemas
	private static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	public Connection conectar()  {
		Connection conexao;
		try {
			Class.forName(DRIVER);
			conexao = DriverManager.getConnection(URL);
			System.out.println("Conectado");
			return conexao;

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return conexao = null;

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


