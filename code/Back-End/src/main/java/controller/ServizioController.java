package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.servizio.Servizio;
import model.servizio.ServizioDAO;

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
	public String saveServizio(@RequestBody Servizio servizio) {
		servizioDAO.saveService(servizio);
		return "Servizio salvato con successo";		
	}
	
	//eliminazione di un servizio dal database
	@PostMapping("/servizi/delete")
	public String deleteServizio(@RequestBody Servizio servizio) {
		servizioDAO.deleteService(servizio);
		return "Servizio eliminato con successo!";
	}
	
	//modifica del costo di un servizio presente nel database
	@PostMapping("/servizi/update")
	public String updateService(@RequestBody Servizio servizio) {
		if(servizioDAO.serviceUpdate(servizio) == Boolean.TRUE)
			return "Servizio modificato con successo!";
		else
			return "Modifica del servizio fallita!, servizio non trovato";
	}

}
