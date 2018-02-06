package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//mapeando o down
@Repository
@Transactional
public class ConexaoDao {

	public static void main(String[] args) throws SQLException{
		Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost/boa","root","admin");

		System.out.println("Conectado!");
		conexao.close();
}}
