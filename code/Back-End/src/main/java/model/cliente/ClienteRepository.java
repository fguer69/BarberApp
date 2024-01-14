package model.cliente;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends ListCrudRepository<Cliente, Integer>{
	
	//Query per l'update dei dati di un cliente, ritorna il numero di righe modificate
	//Se il numero è >= 0 signifca che la query ha avuto successo è che i dati sono stati 
	//Modificati con successo. Se il numero è =0 significa che l'utente non è stato trovato
	//O che l'email inserita è gia stata usata da un'atro utente
	@Modifying
	@Query("update Clienti c set c.nome = ?2, c.cognome = ?3, c.email = ?4, c.password = ?5 where c.id = ?1")
	int updateCliente(int id, String nome, String cognome, String email, String password);
	
	@Query("select Clienti c where c.email = ?1")
	Cliente checkClient(String email);
	

}
