package model.dipendente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DipendenteDAO {
	@Autowired
	private DipendenteRepository repository;
	
	//aggiunta di un nuovo dipendente nel database
	public void saveDipendente(Dipendente dipendente) {
		repository.save(dipendente);
	}
	
	//eliminazione di un dipendente dal database
	public void deleteDipendente(Dipendente dipendente) {
		repository.delete(dipendente);
	}
	
	//aggiornamento informazioni personali di un dipendente
	public Boolean updateEmployee(Dipendente dipendente) throws Exception {
		if(repository.updateDipendente(dipendente.getId(), dipendente.getNome(), dipendente.getCognome(), dipendente.getEmail(), dipendente.getPassword()) > 0)
			return Boolean.TRUE;
		else {
			throw new Exception("Dipendente non trovato o email non valida");
		}
		
	}
}
