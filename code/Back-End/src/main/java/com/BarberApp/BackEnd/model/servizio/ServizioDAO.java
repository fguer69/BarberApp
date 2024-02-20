package com.BarberApp.BackEnd.model.servizio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServizioDAO {
	@Autowired
	ServizioRepository repository;
	
	//Lista di tutti i servizi presenti nel database
	public List<Servizio> getAll(){
		return repository.findAll();
	}
	
	//aggiunta di un servizio nel database
	public void saveService(Servizio servizio) {
		repository.save(servizio);
	}
	
	//eliminazione di un servizio dal database
	public void deleteService(Servizio servizio) {
		repository.delete(servizio);
	}
	
	//modifica di un servizio dal database
	public Boolean serviceUpdate(Servizio servizio) {
		Integer servizioID = servizio.getId();
		if(repository.findByIdAndCosto(servizioID, servizio.getCosto()) > 0)
			return Boolean.TRUE;
		else {
			return Boolean.FALSE;
		}
	}

}
