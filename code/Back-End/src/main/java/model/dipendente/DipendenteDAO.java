package model.dipendente;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DipendenteDAO {
	@Autowired
	private DipendenteRepository repository;
	
	//Ricerca di un dipendente tramite id
	public Optional<Dipendente> getDipendenteById(int id){
		return repository.findById(id);
	}
	
	//controllo se esiste gia un dipendente con una determinata email
	public Boolean checkDipendente(String email) {
		if(repository.checkEmployee(email) == null)
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
		if(repository.updateDipendente(dipendente.getId(), dipendente.getNome(), dipendente.getCognome(), dipendente.getEmail(), dipendente.getPassword()) > 0)
			return Boolean.TRUE;
		else {
			return Boolean.FALSE;
		}
		
	}
}
