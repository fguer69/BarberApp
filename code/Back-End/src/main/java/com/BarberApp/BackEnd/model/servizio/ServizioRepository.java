package com.BarberApp.BackEnd.model.servizio;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ServizioRepository extends ListCrudRepository<Servizio, Integer> {
	
	//aggiornamento del prezzo di un servizio sul database
	@Modifying
	@Query("UPDATE Servizi s SET s.costo = ?2 WHERE s.id = ?1")
	int findByIdAndCosto(Integer id, double prezzo);

}
