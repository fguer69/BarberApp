package model.appuntamento;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.cliente.Cliente;
import model.dipendente.Dipendente;

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
	
	public int checkAppuntamento(Cliente cliente,Appuntamento appuntamento) {
		return repository.checkAppuntamento(cliente.getId(), appuntamento.getDate());
	}
	
	//Ritorno di una lista di appuntamenti di un determinato cliente
	public List<Appuntamento> getAppuntamentiByCliente(Cliente cliente){
		return repository.getAppuntamentiByCliente(cliente.getId());
	}
	
	//Ritorno di una lista di appuntamenti di un determinato dipendente
	public List<Appuntamento> getAppuntamentiByDipendente(Dipendente dipendente){
		return repository.getAppuntamentiByDipendente(dipendente.getId());
	}
	
	//Ritorno della lista completa degli appuntamenti ordinata per data e orario
	public List<Appuntamento> getAllAppuntamenti(){
		return repository.getAppuntamentiTitolare();
	}


}
