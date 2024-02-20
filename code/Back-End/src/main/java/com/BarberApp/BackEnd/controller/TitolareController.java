package com.BarberApp.BackEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareDAO;

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
	public int saveTitolare(@RequestBody Titolare titolare) {
		if(titolareDAO.checkTitolare(titolare.getEmail()) == Boolean.FALSE) {
			titolareDAO.saveTitolare(titolare);
			return 200;
		}
		else
			return 500;
	}
	
	//Eliminazione di un titolare dal sistema
	@PostMapping("/titolari/delete")
	public int delete(@RequestBody Titolare titolare) {
		titolareDAO.deleteTitolare(titolare);
		return 200;
	}
	
	//Check dipendente-email
			@PostMapping("/titolari/check")
			public int check(@RequestBody String email) {
				if(titolareDAO.checkTitolare(email) == Boolean.FALSE)
					return 200;
				else return 500;
			}
	
	//Aggiornamento dei dati dell'account di un titolare
	@PostMapping("/titolari/update")
	public int update(@RequestBody Titolare titolare) {
		Titolare titolare1 = titolareDAO.getTitolareById(titolare.getId()).orElse(null);
		if(titolare1 != null) {
			if(titolare.getEmail() == titolare1.getEmail()) {
				titolareDAO.updateTitolare(titolare);
				return 200;
			}
		else {
			if(titolareDAO.checkTitolare(titolare.getEmail()) == Boolean.FALSE) {
				titolareDAO.updateTitolare(titolare);
				return 200;
			}
			else
				return 500;
		}
	}
	else
		return 501;
	}
	
	//login
	@PostMapping("/titolari/login")
	public Optional<Titolare> login(@RequestParam() String email, @RequestParam() String password){
		return titolareDAO.loginTitolare(email, password);
	}

}
