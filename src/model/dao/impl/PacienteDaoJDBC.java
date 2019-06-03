package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.PacienteDao;
import model.entities.Paciente;

public class PacienteDaoJDBC implements PacienteDao {

	private Connection conn;	
	
	public PacienteDaoJDBC(Connection conn) {	
		this.conn = conn;
	}

	@Override
	public void insert(Paciente obj) {
		PreparedStatement st = null;
		try {
			st = (PreparedStatement) conn.prepareStatement(
					"INSERT INTO paciente "
					+ "(nome, dataNasc, telefone) "
					+ "VALUES "
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getNome());
			st.setDate(2, new java.sql.Date(obj.getDataNasc().getTime()));
			st.setString(3, obj.getTelefone());			
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}else {
				throw new DbException("Erro inesperado tente novamente!");
			}			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Paciente obj) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE paciente "
					+ "SET nome = ?, dataNasc =?, telefone = ? "
					+ "WHERE idPaciente = ?"		
					);
			
			st.setString(1, obj.getNome());
			st.setDate(2, new  java.sql.Date(obj.getDataNasc().getTime()));
			st.setString(3, obj.getTelefone());
			st.setInt(4, obj.getId());	
			st.executeUpdate();							
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}		
	}	
	
	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
						"DELETE FROM paciente WHERE idPaciente = ?"
					);
			
			st.setInt(1, id);
			st.executeQuery();			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Paciente findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM paciente "
					+"WHERE idPaciente = ?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			
			if(rs.next()) {
				Paciente pac = instanciaPaciente(rs);
				return pac;
			}		
			return null;
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}

	@Override
	public List<Paciente> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM paciente "
					);
			
			rs = st.executeQuery();
			
			List<Paciente> list = new ArrayList<>();
			
					
			while(rs.next()) {
				
				Paciente pac =  instanciaPaciente(rs);
				list.add(pac);			
				
			}		
			return list;
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}		
	}	
	
	public Paciente instanciaPaciente(ResultSet rs) throws SQLException {
		Paciente pac = new Paciente();
		pac.setNome(rs.getString("nome"));
		pac.setId(rs.getInt("idPaciente"));
		pac.setDataNasc(rs.getDate("dataNasc"));
		pac.setTelefone(rs.getString("telefone"));
		return pac;
	}

}
