package model.servizio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServizioDAO {
	@Autowired
	ServizioRepository repository;
	
	//aggiunta di un servizio nel database
	public void saveService(Servizio servizio) {
		repository.save(servizio);
	}
	
	//eliminazione di un servizio dal database
	public void deleteService(Servizio servizio) {
		repository.delete(servizio);
	}
	
	//modifica di un servizio dal database
	public Boolean serviceUpdate(Servizio servizio) throws Exception {
		if(repository.updateServizio(servizio.getId(), servizio.getCosto()) > 0)
			return Boolean.TRUE;
		else {
			throw new Exception("Servizio non trovato o inesistente");
		}
	}

}
