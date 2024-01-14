package model.dipendente;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface DipendenteRepository extends ListCrudRepository<Dipendente, Integer> {
	
	//Query per l'update dei dati di un dipendente, ritorna il numero di righe modificate
		//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
		//Modificati con successo. Se il numero è =0 significa che il dipendente non è stato trovato
		//O che l'email inserita è gia stata usata da un'atro utente
		@Modifying
		@Query("update Dipendenti c set c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 where c.id = ?1")
		int updateDipendente(int id, String nome, String cognome, String email, String password);

}
