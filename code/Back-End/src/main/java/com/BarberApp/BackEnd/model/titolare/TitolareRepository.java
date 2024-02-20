package com.BarberApp.BackEnd.model.titolare;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TitolareRepository extends ListCrudRepository<Titolare, Integer> {
	
	//Query per l'update dei dati di un titolare, ritorna il numero di righe modificate
			//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
			//Modificati con successo. Se il numero è =0 significa che il titolare non è stato trovato
			//O che l'email inserita è gia stata usata da un'atro titolare
			@Modifying
			@Query("UPDATE titolari c SET c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 WHERE c.id = ?1;")
			int findByIdAndNomeAndCognomeAndEmailAndPassword(Integer id, String nome, String cognome, String email, String password);
			
			@Query("SELECT * FROM titolari t WHERE t.email = ?1;")
			Titolare getTitolareByEmail(String email);
			
			//login di un titolare
			@Query("SELECT * FROM titolari t WHERE t.email = ?1 AND t.password = ?2;")
			Optional<Titolare> getTitolareByEmailAndPassword(String email, String password);

}
