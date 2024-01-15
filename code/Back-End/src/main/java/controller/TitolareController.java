package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.titolare.Titolare;
import model.titolare.TitolareDAO;

@RestController
public class TitolareController {
	@Autowired
	TitolareDAO titolareDAO;
	
	//Visualizzazione di tutti i titolari del sistema
	@GetMapping("/titolari/get-all")
	public List<Titolare> getAllTitolari(){
		return titolareDAO.getAll();
	}
	
	//aggiunta di un titolare nel database da parte di un'altro titolare
	@PostMapping("/titolari/save")
	public String saveTitolare(@RequestBody Titolare titolare) {
		if(titolareDAO.checkTitolare(titolare.getEmail()) == Boolean.FALSE) {
			titolareDAO.saveTitolare(titolare);
			return "Titolare registrato con successo!";
		}
		else
			return "Email gia in uso, registrazione fallita!";
	}
	
	//Eliminazione di un titolare dal sistema
	@PostMapping("/titolari/delete")
	public String delete(@RequestBody Titolare titolare) {
		titolareDAO.deleteTitolare(titolare);
		return "Titolare eliminato con successo!";
	}
	
	//Aggiornamento dei dati dell'account di un titolare
	@PostMapping("/titolari/update")
	public String update(@RequestBody Titolare titolare) {
		Titolare titolare1 = titolareDAO.getTitolareById(titolare.getId()).orElse(null);
		if(titolare1 != null) {
			if(titolare.getEmail() == titolare1.getEmail()) {
				titolareDAO.updateTitolare(titolare);
				return "Account modificato con successo";
			}
		else {
			if(titolareDAO.checkTitolare(titolare.getEmail()) == Boolean.FALSE) {
				titolareDAO.updateTitolare(titolare);
				return "Account modificato con successo";
			}
			else
				return "Email già in uso, modifica account fallita!";
		}
	}
	else
		return "Titolare non trovato!";
	}

}
