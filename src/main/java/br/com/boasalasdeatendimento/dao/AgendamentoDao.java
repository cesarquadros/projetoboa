package br.com.boasalasdeatendimento.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.boasalasdeatendimento.model.Agendamento;
import br.com.boasalasdeatendimento.util.DataUtil;

@Repository
public class AgendamentoDao extends ConexaoDao{

	public static void main(String[] args) {

		Agendamento a = new Agendamento();
		AgendamentoDao cDao = new AgendamentoDao();
		
		cDao.inserir(a);
	}
	
	public boolean inserir(Agendamento agendamento) {
		
		try {
			final StringBuilder sql = new StringBuilder();

			conectar();
			
			sql.append(" INSERT INTO ");
			sql.append(" 	agendamento ");
			sql.append(" 	(id_horario, id_cliente, id_sala, dt_agendamento) ");
			sql.append(" VALUES ");
			sql.append(" 	(?, ?, ?, ?) ");
			
			stmt = conexao.prepareStatement(sql.toString());
			
			int aux =1;
			
			stmt.setInt(aux++, agendamento.getHorario().getId());
			stmt.setInt(aux++, agendamento.getCliente().getId());
			stmt.setInt(aux++, agendamento.getSala().getId());
			stmt.setString(aux++, agendamento.getDataAgendamentoString());
			
			stmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}
	
public List<Agendamento> meusAgendamentosById(Integer idCliente){
		
		final StringBuilder sql = new StringBuilder();
		
		try {
			conectar();
			
			sql.append(" SELECT * FROM");
			sql.append("	agendamento ");
			sql.append(" WHERE ");
			sql.append(" 	id_cliente = ?");
		
			stmt = conexao.prepareStatement(sql.toString());
			
			stmt.setInt(1, idCliente);
			
			rs = stmt.executeQuery();
			
			Agendamento agendamento = new Agendamento();
			List<Agendamento> listaAgendamentos = new ArrayList<Agendamento>();
			
			HorarioDao horarioDao = new HorarioDao();
			SalaDao salaDao = new SalaDao();
			
			while(rs.next()){
				
				agendamento = new Agendamento();
				
				agendamento.setId(rs.getInt("idAgendamento"));
				agendamento.setStatus(rs.getString("status"));
				agendamento.setHorario(horarioDao.findHorarioById(rs.getInt("id_horario")));
				agendamento.setSala(salaDao.listaSalaByIdComUnidade(rs.getInt("id_sala")));
				agendamento.setDataAgendamentoString(DataUtil.getDateFormatString(rs.getString("dt_agendamento"), "yyyy-MM-dd" ,"dd/MM/yyyy"));
				
				listaAgendamentos.add(agendamento);
				
			}
			return listaAgendamentos;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
