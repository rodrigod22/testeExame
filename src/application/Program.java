package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.PacienteDao;
import model.entities.Paciente;

public class Program {

	public static void main(String[] args) throws ParseException {
	
		
		
		System.out.println("=======TESTE 1=======");
		PacienteDao pacDao = DaoFactory.createPacienteDao();		
		Paciente pac = pacDao.findById(1);		
		System.out.println(pac);
		
		System.out.println("=======TESTE 2=======");		
		List<Paciente> list = pacDao.findAll();
		for (Paciente obj : list) {
			System.out.println(obj);
		}	
		
		System.out.println("=======TESTE 3 INSERT=======");			
		Date data = new SimpleDateFormat("yyyy-MM-dd").parse("1982-05-11"); 			
		Paciente novoPac = new Paciente(null, "Felipe", data , "13-98989898");
		pacDao.insert(novoPac);
		System.out.println("Paciente inserido id = " + novoPac.getId());
		
		System.out.println("=======TESTE 4 UPDATE=======");		
		pac = pacDao.findById(1);
		pac.setNome("Bob");
		pacDao.update(pac);
		System.out.println("Alteração realizada!");
	}

}
