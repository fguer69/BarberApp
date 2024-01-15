package model.titolare;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import model.dipendente.Dipendente;

@Service
public class TitolareDAO {
	@Autowired
	TitolareRepository repository;
	
	//ricerca di un titolare tramite id
	public Optional<Titolare> getTitolareById(int id){
		return repository.findById(id);
	}
	
	//controllo se esiste già un'utente con una determinata email
	public Boolean checkTitolare(String email) {
		if(repository.checkTito(email) == null)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
	}
	
	//visualizzazione di tutti i titolari nel sistema
	public List<Titolare> getAll(){
		return repository.findAll();
	}
	
	//Aggiunta di un titolare nel database
	public void saveTitolare(Titolare titolare) {
		repository.save(titolare);
	}
	
	//Cancellazione di un titolare dal database
	public void deleteTitolare(Titolare titolare) {
		repository.delete(titolare);
	}
	
	//Modifica dei dati di un titolare nel database
	public Boolean updateTitolare(Titolare titolare) {
		if(repository.updateTitolari(titolare.getId(), titolare.getNome(), titolare.getCognome(), titolare.getEmail(), titolare.getPassword()) > 0)
			return Boolean.TRUE;
		else {
			return Boolean.FALSE;
		}
	}

}
