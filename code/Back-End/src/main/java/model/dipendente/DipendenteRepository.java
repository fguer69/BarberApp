package model.dipendente;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

public interface DipendenteRepository extends ListCrudRepository<Dipendente, Integer> {
	
	//Query per l'update dei dati di un dipendente, ritorna il numero di righe modificate
		//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
		//Modificati con successo. Se il numero è =0 significa che il dipendente non è stato trovato
		//O che l'email inserita è gia stata usata da un'atro utente
		@Modifying
		@Query("update Dipendenti c set c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 where c.id = ?1")
		int updateDipendente(int id, String nome, String cognome, String email, String password);
		
		@Query("select Dipendenti d where d.email = ?1")
		Dipendente checkEmployee(String email);
		
		//Query per la selezione dei dipendenti che non hanno appuntamenti in un determinato orario di una determinata data
		@Query("select * from Dipendenti where id not in (select d.id from Dipendenti d inner join Appuntamenti a on d.id = a.dipendente_id where date(a.date) = ?1 and hour(a.time) = ?2)")
		List<Dipendente> selectDipendentiByAppuntamento(DateTime data, DateTime ora);

}
