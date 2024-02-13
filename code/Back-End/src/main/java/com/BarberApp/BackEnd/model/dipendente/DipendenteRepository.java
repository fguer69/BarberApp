package com.BarberApp.BackEnd.model.dipendente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.BarberApp.BackEnd.model.cliente.Cliente;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;
@Repository
public interface DipendenteRepository extends ListCrudRepository<Dipendente, Integer> {
	
	//Query per l'update dei dati di un dipendente, ritorna il numero di righe modificate
		//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
		//Modificati con successo. Se il numero è =0 significa che il dipendente non è stato trovato
		//O che l'email inserita è gia stata usata da un'atro utente
		@Modifying
		@Query("UPDATE Dipendenti c SET c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 WHERE c.id = ?1")
		int findByIdAndNomeAndCognomeAndEmailAndPassword(Integer id, String nome, String cognome, String email, String password);
		
		@Query("SELECT Dipendenti d WHERE d.email = ?1")
		Dipendente findByEmail(String email);
		
		//Query per la selezione dei dipendenti che non hanno appuntamenti in un determinato orario di una determinata data
		@Query("SELECT * FROM Dipendenti WHERE id NOT IN (SELECT d.id FROM Dipendenti d INNER JOIN Appuntamenti a ON d.id = a.dipendente_id WHERE DATE(a.date) = ?1 AND HOUR(a.time) = ?2)")
		List<Dipendente> findAllByAppuntamentiAndNome(DateTime data, DateTime ora);
		
		//login di un dipendente
		@Query("SELECT Dipendenti d WHERE d.email = ?1 AND d.password = ?2")
		Optional<Dipendente> getDipendenteByEmailAndPassword(String email, String password);
		
		

}
