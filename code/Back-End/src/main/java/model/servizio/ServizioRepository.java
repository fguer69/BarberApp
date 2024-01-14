package model.servizio;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface ServizioRepository extends ListCrudRepository<Servizio, Integer> {
	
	//aggiornamento del prezzo di un servizio sul database
	@Modifying
	@Query("update Servizi s set s.costo = ?2 where s.id = ?1")
	int updateServizio(int id, double prezzo);

}
