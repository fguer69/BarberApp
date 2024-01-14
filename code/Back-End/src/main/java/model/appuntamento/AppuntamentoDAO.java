package model.appuntamento;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppuntamentoDAO {
	@Autowired
	private AppuntamentoRepository repository;
	
	//inserimento di un nuovo appuntamento nel database
	public void saveAppointment(Appuntamento appuntamento) {
		repository.save(appuntamento);
	}
	
	//rimozione di un'appuntamento dal database
	public void deleteAppointment(Appuntamento appuntamento) {
		repository.delete(appuntamento);
	}
	
	/* aggiornamento di un appuntamento nel database
	public void updateAppointment(Appuntamento appuntamento) {
		
	}*/


}
