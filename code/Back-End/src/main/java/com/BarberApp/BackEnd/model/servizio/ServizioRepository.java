package com.BarberApp.BackEnd.model.servizio;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServizioRepository extends ListCrudRepository<Servizio, Integer> {
	
	//aggiornamento del prezzo di un servizio sul database
	@Modifying
	@Query("UPDATE servizi s SET s.costo = ?1 WHERE s.id = ?2;")
	int findByIdAndCosto(Integer id, double prezzo);

}
