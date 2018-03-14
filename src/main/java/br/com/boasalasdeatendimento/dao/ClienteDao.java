package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Autenticacao;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.util.DataUtil;

@Repository
public class ClienteDao{

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
		
		//cDao.inserir(cliente);
		
		cDao.clienteListAll();
	}
	
	@Autowired
	private ConexaoDao conexaoDao;
	
	public Cliente inserir(Cliente cliente) {
		
		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {

			sql.append(" INSERT INTO ");
			sql.append(" 	cliente ");
			sql.append(" 	(nome, sobrenome, tel_fixo, tel_celular, cpf, email, sexo, id_autenticacao) ");
			sql.append(" VALUES ");
			sql.append(" 	(?, ?, ?, ?, ?, ?, ?, ?) ");
			
			stmt = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			
			int aux =1;
			
			stmt.setString(aux++, cliente.getNome());
			stmt.setString(aux++, cliente.getSobrenome());
			stmt.setString(aux++, cliente.getTelFixo().replace("(", "").replace(")", "").replace("-", ""));
			stmt.setString(aux++, cliente.getTelCelular().replace("(", "").replace(")", "").replace("-", ""));
			stmt.setString(aux++,cliente.getCpf().replace("-", "").replace(".", ""));
			stmt.setString(aux++, cliente.getEmail());
			stmt.setString(aux++, cliente.getSexo());
			//stmt.setString(aux++, DataUtil.getDateFormatString(cliente.getDataNascimentoString() ,"dd/MM/yyyy", "yyyyMMdd"));
			stmt.setInt(aux++, cliente.getAutenticacao().getId());
			
			stmt.execute();
			
			ResultSet rs = stmt.getGeneratedKeys();
			
			while (rs.next()) {
				cliente.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return cliente;
	}
	
	public Cliente findByIdAutenticacao(Autenticacao autenticacao){
		
		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT * FROM");
			sql.append("	cliente ");
			sql.append(" WHERE ");
			sql.append(" 	id_autenticacao = ?");
		
			stmt = conexao.prepareStatement(sql.toString());
			
			stmt.setInt(1, autenticacao.getId());
			
			ResultSet rs = stmt.executeQuery();
			
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
				//cliente.setDataNascimentoString(DataUtil.getDateFormatString(rs.getString("dt_nasc"), "yyyy-MM-dd" ,"dd/MM/yyyy"));
				
				return cliente;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}
	
	
	public List<Cliente> clienteListAll(){
		
		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT * FROM");
			sql.append("	cliente ");
		
			stmt = conexao.prepareStatement(sql.toString());
			
			ResultSet rs = stmt.executeQuery();
			
			List<Cliente> listaClientes = new ArrayList<Cliente>();
			Cliente cliente;
			
			while(rs.next()){
				
				cliente = new Cliente();
				
				cliente.setId(rs.getInt("idCliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setSobrenome(rs.getString("sobrenome"));
				cliente.setTelFixo(rs.getString("tel_fixo"));
				cliente.setTelCelular(rs.getString("tel_celular"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEmail(rs.getString("email"));
				cliente.setSexo(rs.getString("sexo"));
				//cliente.setDataNascimentoString(DataUtil.getDateFormatString(rs.getString("dt_nasc"), "yyyy-MM-dd" ,"dd/MM/yyyy"));
				
				listaClientes.add(cliente);
			}
			return listaClientes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conexaoDao.fecharConexao(stmt, conexao);
		}
		return null;
	}
	
	
	
	public Boolean findByIdCpf(String cpf){
		
		final StringBuilder sql = new StringBuilder();
		
		Connection conexao = conexaoDao.conectar();
		PreparedStatement stmt = null;
		
		try {
			
			sql.append(" SELECT * FROM");
			sql.append("	cliente ");
			sql.append(" WHERE ");
			sql.append(" 	cpf = ?");
		
			stmt = conexao.prepareStatement(sql.toString());
			
			stmt.setString(1, cpf.replace("-", "").replace(".", ""));
			
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()){
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
