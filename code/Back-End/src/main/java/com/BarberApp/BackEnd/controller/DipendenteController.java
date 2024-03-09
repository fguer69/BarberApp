package com.BarberApp.BackEnd.controller;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


import com.BarberApp.BackEnd.StringToDateConverter.StringToDateTimeConverter;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;


@RestController
public class DipendenteController {
	
	@Autowired
	private DipendenteDAO dipendenteDAO;

	
	//Check dipendente-email
		@PostMapping("/dipendenti/check")
		public int check(@RequestBody String email) {
			if(dipendenteDAO.checkDipendente(email) == Boolean.FALSE)
				return 200;
			else return 500;
		}
	
	//visualizzazione di tutti i dipendenti nel sistema
	@GetMapping("/dipendenti/get-all")
	public List<Dipendente> getAllEmployee(){
		return dipendenteDAO.getEmployee();
	}
	
	//registrazione di un nuovo dipendente nel sistema
	@PostMapping("/dipendenti/save")
	public int saveEmployee(@RequestBody Dipendente dipendente) {
		if(dipendenteDAO.checkDipendente(dipendente.getEmail()) == Boolean.FALSE) {
			dipendenteDAO.saveDipendente(dipendente);
			return 200;
		}
		else
			return 500;
	}
	
	//Eliminazione di un dipendente dal sistema
	@PostMapping("/dipendenti/delete")
	public int delete(@RequestBody Dipendente dipendente) {
		dipendenteDAO.deleteDipendente(dipendente);
		return 200;
	}
	
	//Aggiornamento dei dati dell'account di un dipendente
	@PostMapping("/dipendenti/update")
	public int update(@RequestBody Dipendente dipendente) {
		Dipendente dipendente1 = dipendenteDAO.getDipendenteById(dipendente.getId()).orElse(null);
		if(dipendente1 != null) {
			if(dipendente.getEmail().equals(dipendente1.getEmail())) {
				dipendenteDAO.updateEmployee(dipendente);
				return 200;
			}
			else {
				if(dipendenteDAO.checkDipendente(dipendente.getEmail()) == Boolean.FALSE) {
					dipendenteDAO.updateEmployee(dipendente);
					return 200;
				}
				else
					return 500;
			}
		}
		else
			return 501;
	}
	//Visualizzazione di tutti i dipendenti disponibili per una determinata data e ora
	@PostMapping("/dipendenti/dipendentiDisponibili")
	public List<Dipendente> getDipendentiByDate(@RequestParam() String data, @RequestParam() String ora){
			System.out.println(data+" "+ora);
		DateTimeZone.setDefault(DateTimeZone.UTC);
		String Ora = ora.replace("Z", "");
		DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		//DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		DateTime date = DateTime.parse(data,dateFormatter);
		DateTime time = DateTime.parse(Ora, timeFormatter);
		System.out.println(date+" "+time);
			return dipendenteDAO.getEmployeeByDate(date,time);
	}
	//Login di un dipendente
	//login
		@PostMapping("/dipendenti/login")
		public Optional<Dipendente> login(@RequestParam() String email, @RequestParam() String password){
			return dipendenteDAO.loginDipendente(email, password);
		}

		@PostMapping("/dipendenti/getById")
		public Optional<Dipendente> getDipendenteById(@RequestBody int id){
			return dipendenteDAO.getDipendenteById(id);
		}

	@PostMapping("/dipendenti/getDipendenteByEmail")
	public Dipendente getDipendenteByEmail(@RequestBody() String email){
		return dipendenteDAO.getDipendenteByEmail(email);

	}

}
