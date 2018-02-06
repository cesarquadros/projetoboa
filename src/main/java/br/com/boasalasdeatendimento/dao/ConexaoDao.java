package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//mapeando o down
@Repository
@Transactional
public class ConexaoDao {

	public void conectar() throws SQLException {
		
		Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/boa", "root", "admin");

		System.out.println("Conectado!");
		conexao.close();
	}
}
