package model.dao;

import db.DB;
import model.dao.impl.PacienteDaoJDBC;

public class DaoFactory {
	
	public static PacienteDao createPacienteDao() {
		return new PacienteDaoJDBC(DB.getConnection());
	}

}
