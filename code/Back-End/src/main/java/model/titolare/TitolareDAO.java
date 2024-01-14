package model.titolare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import model.dipendente.Dipendente;

@Service
public class TitolareDAO {
	@Autowired
	TitolareRepository repository;
	
	//Aggiunta di un titolare nel database
	public void saveTitolare(Titolare titolare) {
		repository.save(titolare);
	}
	
	//Cancellazione di un titolare dal database
	public void deleteTitolare(Titolare titolare) {
		repository.delete(titolare);
	}
	
	//Modifica dei dati di un titolare nel database
	public Boolean updateTitolare(Titolare titolare) throws Exception {
		if(repository.updateTitolari(titolare.getId(), titolare.getNome(), titolare.getCognome(), titolare.getEmail(), titolare.getPassword()) > 0)
			return Boolean.TRUE;
		else {
			throw new Exception("Titolare non trovato o email non valida");
		}
	}

}
