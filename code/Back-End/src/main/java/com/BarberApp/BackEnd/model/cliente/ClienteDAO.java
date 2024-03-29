package com.BarberApp.BackEnd.model.cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.BarberApp.BackEnd.model.titolare.Titolare;

@Service
public class ClienteDAO {
	@Autowired
	private ClienteRepository repository;

	//ricerca di un cliente tramite id
	public Optional<Cliente> getClienteById(int id) {
		Integer ID = id;
		return repository.findById(ID);
	}
	
	//controllo se esiste già un'utente con una determinata email
	public Boolean checkCliente(String email) {
		if(repository.getClienteByEmail(email) == null)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
	}

	public Cliente getClienteByEmail(String email){
		return repository.getClienteByEmail(email);
	}
	
	//Inserimento di un nuovo utente nel database
	public void saveCliente(Cliente cliente) {
		repository.save(cliente);
	}
	
	//ritorno di tutti i clienti nel database
	public List<Cliente> getAllClienti(){
		return repository.findAll();
	}
	
	//cancellazione di un cliente nel database
	public void deleteCliente(Cliente cliente){
		repository.delete(cliente);
	}
	
	//aggiornamento dati utente
	public Boolean updateClient(Cliente cliente){
		Integer clienteID = cliente.getId();
		repository.save(cliente);
			return Boolean.TRUE;
	}
	
	//login
	public Optional<Cliente> loginCliente(String email, String password) {
		return repository.getClienteByEmailAndPassword(email, password);		
	}

}
