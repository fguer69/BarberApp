package com.BarberApp.BackEnd.model.dipendente;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.NamedNativeQuery;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BarberApp.BackEnd.model.cliente.Cliente;

import org.joda.time.DateTime;

@Repository
public interface DipendenteRepository extends ListCrudRepository<Dipendente, Integer> {
	
	//Query per l'update dei dati di un dipendente, ritorna il numero di righe modificate
		//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
		//Modificati con successo. Se il numero è =0 significa che il dipendente non è stato trovato
		//O che l'email inserita è gia stata usata da un'atro utente
		@Modifying
		@Query(value = "UPDATE dipendenti c SET c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 WHERE c.id = ?1", nativeQuery = true)
		int findByIdAndNomeAndCognomeAndEmailAndPassword(Integer id, String nome, String cognome, String email, String password);
		
		@Query(value = "SELECT * FROM dipendenti d WHERE d.email = ?1", nativeQuery = true)
		Dipendente findByEmail(String email);
		
		//Query per la selezione dei dipendenti che non hanno appuntamenti in un determinato orario di una determinata data
		@Query(value = "SELECT * FROM dipendenti d WHERE d.id NOT IN (SELECT c.id FROM dipendenti c INNER JOIN appuntamenti a ON c.id = a.dipendente_id WHERE DATE(a.date) = DATE(?1) AND TIME(a.time) = TIME(?2))",nativeQuery = true)
		List<Dipendente> findAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime(DateTime data,DateTime ora);
		
		//login di un dipendente
		@Query(value = "SELECT * FROM dipendenti d WHERE d.email = ?1 AND d.password = ?2", nativeQuery = true)
		Optional<Dipendente> getDipendenteByEmailAndPassword(String email, String password);
		
		

}
