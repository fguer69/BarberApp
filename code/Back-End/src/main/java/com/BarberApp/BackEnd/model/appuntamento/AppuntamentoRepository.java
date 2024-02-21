package com.BarberApp.BackEnd.model.appuntamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BarberApp.BackEnd.model.cliente.Cliente;

import jakarta.persistence.NamedNativeQuery;
import org.joda.time.DateTime;
@Repository
public interface AppuntamentoRepository extends ListCrudRepository<Appuntamento, Integer> {

	
	@Query(value = "SELECT COUNT(*) AS numAppuntamenti FROM appuntamenti WHERE cliente_id = ?1 AND DATE(date) = DATE(?2)", nativeQuery = true)
	int countAppointmentsByCliente_IdAndDate(Integer ClienteId,DateTime date);
	
	@Query(value = "SELECT * FROM appuntamenti WHERE cliente_id = ?1 ORDER BY date, time", nativeQuery = true)
	List<Appuntamento> findByClienteIdOrderById(Integer ClienteId);

	@Query(value = "SELECT * FROM appuntamenti WHERE dipendente_id = ?1 ORDER BY date, time", nativeQuery = true)
	List<Appuntamento> findByDipendenteId(Integer DipendenteId);
	
	@Query(value = "SELECT * FROM appuntamenti ORDER BY date, time", nativeQuery = true)
	List<Appuntamento> findAllByOrderByDate();
	
	

}
