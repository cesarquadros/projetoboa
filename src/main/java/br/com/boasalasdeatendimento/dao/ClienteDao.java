package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.Util;

import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.util.DataUtil;

@Repository
public class ClienteDao extends ConexaoDao{

	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		
		cliente.setNome("Cesar");
		cliente.setSobrenome("Test");
		cliente.setTelFixo("1156214658");
		cliente.setTelCelular("11980635589");
		cliente.setCpf("35765289829");
		cliente.setEmail("teste@teste");
		cliente.setSexo("M");
		cliente.setDataNascimentoString("2018-01-01");
		cliente.setId(1);
		
		ClienteDao cDao = new ClienteDao();
		
		cDao.inserir(cliente);
	}
	
	public Cliente inserir(Cliente cliente) {
		
		final StringBuilder sql = new StringBuilder();
		try {

			conectar();
			
			sql.append(" INSERT INTO ");
			sql.append(" 	cliente ");
			sql.append(" 	(nome, sobrenome, tel_fixo, tel_celular, cpf, email, sexo, dt_nasc, id_autenticacao) ");
			sql.append(" VALUES ");
			sql.append(" 	(?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			
			stmt = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			
			int aux =1;
			
			stmt.setString(aux++, cliente.getNome());
			stmt.setString(aux++, cliente.getSobrenome());
			stmt.setString(aux++, cliente.getTelFixo());
			stmt.setString(aux++, cliente.getTelCelular());
			stmt.setString(aux++,cliente.getCpf());
			stmt.setString(aux++, cliente.getEmail());
			stmt.setString(aux++, cliente.getSexo());
			stmt.setString(aux++, cliente.getDataNascimentoString());
			stmt.setInt(aux++, cliente.getAutenticacao().getId());
			
			stmt.execute();
			
			rs = stmt.getGeneratedKeys();
			
			while (rs.next()) {
				cliente.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		return cliente;
	}
	
	public Cliente findByIdAutenticacao(Autenticacao autenticacao){
		
		final StringBuilder sql = new StringBuilder();
		
		try {
			conectar();
			
			sql.append(" SELECT * FROM");
			sql.append("	cliente ");
			sql.append(" WHERE ");
			sql.append(" 	id_autenticacao = ?");
		
			stmt = conexao.prepareStatement(sql.toString());
			
			stmt.setInt(1, autenticacao.getId());
			
			rs = stmt.executeQuery();
			
			Cliente cliente = new Cliente();
			
			while(rs.next()){
				cliente.setId(rs.getInt("idCliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setSobrenome(rs.getString("sobrenome"));
				cliente.setTelFixo(rs.getString("tel_fixo"));
				cliente.setTelCelular(rs.getString("tel_celular"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSexo(rs.getString("sexo"));
				cliente.setAutenticacao(autenticacao);
				cliente.setDataNascimentoString(DataUtil.getDateFormatString(rs.getString("dt_nasc"), "yyyy-MM-dd" ,"dd/MM/yyyy"));
				
				return cliente;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
