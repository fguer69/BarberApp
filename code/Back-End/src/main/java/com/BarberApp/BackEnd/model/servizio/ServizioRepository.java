package com.BarberApp.BackEnd.model.servizio;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServizioRepository extends ListCrudRepository<Servizio, Integer> {
	
	//aggiornamento del prezzo di un servizio sul database
	@Modifying
	@Query(value = "UPDATE servizi s SET s.costo = ?1 WHERE s.id = ?2", nativeQuery = true)
	int findByIdAndCosto(Integer id, double prezzo);

}
