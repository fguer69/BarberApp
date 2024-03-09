package com.BarberApp.BackEnd.model.dipendente;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import jakarta.persistence.EntityManager;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BarberApp.BackEnd.model.cliente.Cliente;



@Service
public class DipendenteDAO {
	@Autowired
	private DipendenteRepository repository;

	//Ricerca di un dipendente tramite id
	public Optional<Dipendente> getDipendenteById(int id){
		Integer dipendenteID = id;
		return repository.findById(dipendenteID);
	}
	
	//controllo se esiste gia un dipendente con una determinata email
	public Boolean checkDipendente(String email) {
		if(repository.findByEmail(email) == null)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
	}
	
	//elenco di tutti i dipendenti nel sistema
	public List<Dipendente> getEmployee(){
		return repository.findAll();
	}
	
	//aggiunta di un nuovo dipendente nel database
	public void saveDipendente(Dipendente dipendente) {
		repository.save(dipendente);
	}
	
	//eliminazione di un dipendente dal database
	public void deleteDipendente(Dipendente dipendente) {
		repository.delete(dipendente);
	}
	
	//aggiornamento informazioni personali di un dipendente
	public Boolean updateEmployee(Dipendente dipendente){
		Integer dipendenteID = dipendente.getId();
		repository.save(dipendente);
			return Boolean.TRUE;

		
	}
	//elenco di tutti i dipendenti disponibili per una determinata data ed una determinata ora
	public List<Dipendente> getEmployeeByDate(DateTime data, DateTime ora){
		return repository.findAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime(data, ora);
	}
	
	//login di un dipendente
	//login
		public Optional<Dipendente> loginDipendente(String email, String password) {
			return repository.getDipendenteByEmailAndPassword(email, password);		
		}

	public Dipendente getDipendenteByEmail(String email){
		return repository.findByEmail(email);
	}
}
