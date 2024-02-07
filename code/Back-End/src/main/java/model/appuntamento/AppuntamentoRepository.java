package model.appuntamento;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import model.cliente.Cliente;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

public interface AppuntamentoRepository extends ListCrudRepository<Appuntamento, Integer> {
	@Modifying
	@Query("select count(*) as numAppuntamenti from Appuntamenti where cliente_id = ?1 and date(date) = date(?2)")
	int checkAppuntamento(int ClienteId,DateTime appuntamento);
	

}
