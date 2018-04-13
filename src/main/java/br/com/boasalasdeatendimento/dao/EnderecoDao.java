package br.com.boasalasdeatendimento.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.model.Cliente;
import br.com.boasalasdeatendimento.model.Endereco;
import br.com.boasalasdeatendimento.model.Horario;
import br.com.boasalasdeatendimento.model.RelatorioAgendamento;
import br.com.boasalasdeatendimento.model.Sala;
import br.com.boasalasdeatendimento.model.Unidade;
import br.com.boasalasdeatendimento.util.DataUtil;
import br.com.boasalasdeatendimento.util.Util;

@Repository
public class EnderecoDao {

	@Autowired
	private ConexaoDao conexaoDao;

	public Endereco enderecoById(Integer idEndereco) {

		final StringBuilder sql = new StringBuilder();

		ConexaoDao c = new ConexaoDao();
		
		Connection conexao = c.conectar();
		PreparedStatement stmt = null;

		try {

			sql.append(" SELECT * ");
			sql.append(" FROM ");
			sql.append(" 	endereco ");
			sql.append(" WHERE ");
			sql.append(" 	idEndereco = ? ");

			stmt = conexao.prepareStatement(sql.toString());

			stmt.setInt(1, idEndereco);

			ResultSet rs = stmt.executeQuery();

			Endereco endereco = new Endereco();

			while (rs.next()) {
				
				endereco.setId(rs.getInt("idEndereco"));
				endereco.setLogradouro(rs.getString("logradouro"));
				endereco.setNumero(rs.getInt("numero"));
				endereco.setCep(rs.getInt("cep"));
				endereco.setComplemento(rs.getString("complemento"));
				endereco.setBairro(rs.getString("bairro"));
				endereco.setCidade(rs.getString("cidade"));
				endereco.setEstado(rs.getString("estado"));
			}
			return endereco;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			c.fecharConexao(stmt, conexao);
		}
		return null;
	}
}
