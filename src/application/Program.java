package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.PacienteDao;
import model.entities.Paciente;

public class Program {

	public static void main(String[] args) {
	
		PacienteDao pacDao = DaoFactory.createPacienteDao();
		
		Paciente pac = pacDao.findById(1);
		
		System.out.println(pac);
		
	}

}
