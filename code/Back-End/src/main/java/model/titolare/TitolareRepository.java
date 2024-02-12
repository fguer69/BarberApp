package model.titolare;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface TitolareRepository extends ListCrudRepository<Titolare, Integer> {
	
	//Query per l'update dei dati di un titolare, ritorna il numero di righe modificate
			//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
			//Modificati con successo. Se il numero è =0 significa che il titolare non è stato trovato
			//O che l'email inserita è gia stata usata da un'atro titolare
			@Modifying
			@Query("update Titolari c set c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 where c.id = ?1")
			int updateTitolari(int id, String nome, String cognome, String email, String password);
			
			@Query("select Titolari t where t.email = ?1")
			Titolare checkTito(String email);
			
			//login di un titolare
			@Query("select Titolari t where t.email = ?1 and t.password = ?2")
			Optional<Titolare> login(String email, String password);

}
