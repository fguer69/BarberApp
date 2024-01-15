package controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import model.cliente.Cliente;
import model.cliente.ClienteDAO;

@RestController
public class ClientsController {
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	//visualizzazione di tutti i clienti registrati nel sistema
	@GetMapping("/clienti/get-all")
	public List<Cliente> getAllClients(){
		return clienteDAO.getAllClienti();	
	}
	
	//registrazione di un'utente nel sistema
	@PostMapping("/clienti/save")
	public String save(@RequestBody Cliente cliente) {
		if(clienteDAO.checkCliente(cliente.getEmail()) == Boolean.FALSE) {
			clienteDAO.saveCliente(cliente);
			return "Cliente registrato con successo!";
		}
		else
			return "Email già in uso, registrazione fallita!";
	}
	
	//Eliminazione di un'utente dal sistema
	@PostMapping("/clienti/delete")
	public String delete(@RequestBody Cliente cliente) {
			clienteDAO.deleteCliente(cliente);
			return "Account eliminato con successo!";
	}
	
	//Aggiornamento dei dati dell'account di un cliente
	@PostMapping("/clienti/update")
	public String update(@RequestBody Cliente cliente) {
		Cliente cliente1 = clienteDAO.getClienteById(cliente.getId()).orElse(null);
		if(cliente1 != null) {
			if(cliente.getEmail() == cliente1.getEmail()) {
				clienteDAO.updateClient(cliente);
				return "Account modificato con successo";
			}
			else {
				if(clienteDAO.checkCliente(cliente.getEmail()) == Boolean.FALSE) {
					clienteDAO.updateClient(cliente);
					return "Account modificato con successo!";
				}
				else
					return "Email già in uso, modifica account fallita!";
			}
		}
		else
			return "Utente non trovato!";
	}
 
}
