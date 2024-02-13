package com.BarberApp.BackEnd.model.appuntamento;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

@Service
public class AppuntamentoDAO {
	@Autowired
	private AppuntamentoRepository repository;
	
	//Ricerca di tutti gli appuntamenti nel database
	public List<Appuntamento> getAll(){
		return repository.findAll();
	}

	//inserimento di un nuovo appuntamento nel database
	public void saveAppointment(Appuntamento appuntamento) {
		repository.save(appuntamento);
	}
	
	//rimozione di un'appuntamento dal database
	public void deleteAppointment(Appuntamento appuntamento) {
		repository.delete(appuntamento);
	}
	/* aggiornamento di un appuntamento nel database
	public void updateAppointment(Appuntamento appuntamento) {
		
	}*/
	
	public int checkAppuntamento(Cliente cliente, Appuntamento appuntamento) {
	    Integer clienteID = cliente.getId();
	    return repository.findAppuntamentoByDateAndCliente(clienteID, appuntamento.getDate());
	}
	
	//Ritorno di una lista di appuntamenti di un determinato cliente
	public List<Appuntamento> getAppuntamentiByCliente(Cliente cliente){
		Integer clienteID = cliente.getId();
		return repository.findByClienteIdOrderById(clienteID);
	}
	
	//Ritorno di una lista di appuntamenti di un determinato dipendente
	public List<Appuntamento> getAppuntamentiByDipendente(Dipendente dipendente){
		Integer dipendenteID = dipendente.getId();
		return repository.findByDipendenteId(dipendenteID);
	}
	
	//Ritorno della lista completa degli appuntamenti ordinata per data e orario
	public List<Appuntamento> getAppuntamentiOrdered(){
		return repository.findAllByOrderByDate();
	}


}
