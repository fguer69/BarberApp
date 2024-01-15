package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.dipendente.Dipendente;
import model.dipendente.DipendenteDAO;

@RestController
public class DipendenteController {
	
	@Autowired
	private DipendenteDAO dipendenteDAO;
	
	//visualizzazione di tutti i dipendenti nel sistema
	@GetMapping("/dipendenti/get-all")
	public List<Dipendente> getAllEmployee(){
		return dipendenteDAO.getEmployee();
	}
	
	//registrazione di un nuovo dipendente nel sistema
	@PostMapping("/dipendenti/save")
	public String saveEmployee(@RequestBody Dipendente dipendente) {
		if(dipendenteDAO.checkDipendente(dipendente.getEmail()) == Boolean.FALSE) {
			dipendenteDAO.saveDipendente(dipendente);
			return "Dipendente registrato con successo!";
		}
		else
			return "Email già in uso, registrazione dipendente fallita!";
	}
	
	//Eliminazione di un dipendente dal sistema
	@PostMapping("/dipendenti/delete")
	public String delete(@RequestBody Dipendente dipendente) {
		dipendenteDAO.deleteDipendente(dipendente);
		return "Dipendente eliminato con successo!";
	}
	
	//Aggiornamento dei dati dell'account di un dipendente
	@PostMapping("/dipendenti/update")
	public String update(@RequestBody Dipendente dipendente) {
		Dipendente dipendente1 = dipendenteDAO.getDipendenteById(dipendente.getId()).orElse(null);
		if(dipendente1 != null) {
			if(dipendente.getEmail() == dipendente1.getEmail()) {
				dipendenteDAO.updateEmployee(dipendente);
				return "Account modificato con successo";
			}
			else {
				if(dipendenteDAO.checkDipendente(dipendente.getEmail()) == Boolean.FALSE) {
					dipendenteDAO.updateEmployee(dipendente);
					return "Account modificato con successo!";
				}
				else
					return "Email gia in uso, modifica account fallita!";
			}
		}
		else
			return "Utente non trovato!";
	}

}
