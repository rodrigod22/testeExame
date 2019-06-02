package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Paciente obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

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
				Paciente pac = new Paciente();
				pac.setNome(rs.getString("nome"));
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
		// TODO Auto-generated method stub
		return null;
	}

}
