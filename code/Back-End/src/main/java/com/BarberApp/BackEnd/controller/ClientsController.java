package com.BarberApp.BackEnd.controller;

import java.util.List;
import java.util.Optional;

import com.BarberApp.BackEnd.MailService.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.titolare.Titolare;

@RestController
public class ClientsController {
	
	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	private EmailServiceImpl mailService;
	
	//visualizzazione di tutti i clienti registrati nel sistema
	@GetMapping("/clienti/get-all")
	public List<Cliente> getAllClients(){
		return clienteDAO.getAllClienti();	
	}

	
	//registrazione di un'utente nel sistema
	@PostMapping("/clienti/save")
	public int save(@RequestBody Cliente cliente) {
		if(clienteDAO.checkCliente(cliente.getEmail()) == Boolean.TRUE){
			return 500;
		}
		else{
			clienteDAO.saveCliente(cliente);
			return 200;
		}
		/*if(clienteDAO.checkCliente(cliente.getEmail()) == Boolean.FALSE) {
			clienteDAO.saveCliente(cliente);
			return 200;
		}
		else
			return 500;*/
	}
	
	//Eliminazione di un'utente dal sistema
	@PostMapping("/clienti/delete")
	public int delete(@RequestBody Cliente cliente) {
			clienteDAO.deleteCliente(cliente);
			return 200;
	}
	
	//Check cliente-email
	@PostMapping("/cliente/check")
	public int check(@RequestBody String email) {
		if(!clienteDAO.checkCliente(email)) {
			return 200;
		}
		else return 500;
	}
	
	//Aggiornamento dei dati dell'account di un cliente
	@PostMapping("/clienti/update")
	public int update(@RequestBody Cliente cliente) {
		Cliente cliente1 = clienteDAO.getClienteById(cliente.getId()).orElse(null);
		if(cliente1 != null) {
			if(cliente.getEmail().equals(cliente1.getEmail())) {
				clienteDAO.updateClient(cliente);
				return 200;
			}
			else {
				if(clienteDAO.checkCliente(cliente.getEmail()) == Boolean.FALSE) {
					clienteDAO.updateClient(cliente);
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
	@PostMapping("/clienti/login")
	public Optional<Cliente> login(@RequestParam() String email, @RequestParam() String password){
		return clienteDAO.loginCliente(email, password);
	}

	@PostMapping("/clienti/getClienteByEmail")
	public Cliente getClienteByEmail(@RequestBody() String email){
		return clienteDAO.getClienteByEmail(email);

	}

	
	
 
}
