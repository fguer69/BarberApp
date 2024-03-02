package com.BarberApp.BackEnd.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareDAO;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

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
		String password = titolare.getPassword();
		String passwordHashed = null;
		try{
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			passwordHashed = "";
			for (int i=0; i<hash.length; i++)
			{
				passwordHashed += Integer.toHexString((hash[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3);
			}
		}
		catch (java.security.NoSuchAlgorithmException e)
		{
			System.err.println(e);
		}
		titolare.setPassword(passwordHashed);
		if(titolareDAO.checkTitolare(titolare.getEmail()) == Boolean.FALSE && passwordHashed != null) {
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
