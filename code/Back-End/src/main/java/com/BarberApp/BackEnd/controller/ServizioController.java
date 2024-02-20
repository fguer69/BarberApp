package com.BarberApp.BackEnd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.servizio.ServizioDAO;

@RestController
public class ServizioController {
	
	@Autowired
	private ServizioDAO servizioDAO;
	
	//visualizzazione di tutti i servizi registrati sul sistema
	@GetMapping("/servizi/get-all")
	public List<Servizio> getAllServices(){
		return servizioDAO.getAll();
	}
	
	//salvataggio di un nuovo servizio nel database
	@PostMapping("/servizi/save")
	public int saveServizio(@RequestBody Servizio servizio) {
		System.out.println(servizio);
		servizioDAO.saveService(servizio);
		return 200;
	}
	
	//eliminazione di un servizio dal database
	@PostMapping("/servizi/delete")
	public int deleteServizio(@RequestBody Servizio servizio) {
		servizioDAO.deleteService(servizio);
		return 200;
	}
	
	//modifica del costo di un servizio presente nel database
	@PostMapping("/servizi/update")
	public int updateService(@RequestBody Servizio servizio) {
		if(servizioDAO.serviceUpdate(servizio) == Boolean.TRUE)
			return 200;
		else
			return 501;
	}

}
