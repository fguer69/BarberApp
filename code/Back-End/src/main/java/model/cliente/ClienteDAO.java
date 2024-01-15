package model.cliente;

//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class ClienteDAO {
	@Autowired
	private ClienteRepository repository;
	
	//ricerca di un cliente tramite id
	public Optional<Cliente> getClienteById(int id) {
		return repository.findById(id);
	}
	
	//controllo se esiste già un'utente con una determinata email
	public Boolean checkCliente(String email) {
		if(repository.checkClient(email) == null)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
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
		if(repository.updateCliente(cliente.getId(), cliente.getNome(), cliente.getCognome(), cliente.getEmail(), cliente.getPassword()) > 0)
			return Boolean.TRUE;
		else {
			return Boolean.FALSE;
		}
	}

}
