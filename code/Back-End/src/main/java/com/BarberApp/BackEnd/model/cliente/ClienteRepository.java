package com.BarberApp.BackEnd.model.cliente;
import java.util.Optional;

import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.BarberApp.BackEnd.model.titolare.Titolare;

@Repository
public interface ClienteRepository extends ListCrudRepository<Cliente, Integer>{
	
	//Query per l'update dei dati di un cliente, ritorna il numero di righe modificate
	//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
	//Modificati con successo. Se il numero è =0 significa che l'utente non è stato trovato
	//O che l'email inserita è gia stata usata da un'atro utente
	@Modifying
	@Query(value = "UPDATE clienti c SET c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 WHERE c.id = ?1", nativeQuery = true)
	int findByIdAndNomeAndCognomeAndEmailAndPassword(Integer id, String nome, String cognome, String email, String password);
	
	@Query(value = "SELECT * FROM clienti c WHERE c.email = ?1", nativeQuery = true)
	Cliente getClienteByEmail(String email);
	
	//login di un cliente
	@Query(value = "SELECT * FROM clienti c WHERE c.email = ?1 AND c.password = ?2", nativeQuery = true)
	Optional<Cliente> getClienteByEmailAndPassword(String email, String password);
	

}
