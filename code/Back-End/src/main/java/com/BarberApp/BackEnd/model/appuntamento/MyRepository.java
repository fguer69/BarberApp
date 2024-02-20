package com.BarberApp.BackEnd.model.appuntamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class MyRepository {
    @PersistenceContext
    EntityManager entityManager;
    public int countAppointmentByDateAndClient(int clientId, LocalDateTime date){
        String queryString = "SELECT COUNT(*) FROM appuntamenti WHERE cliente_id = " + clientId +
                " AND DATE(date) = DATE('" + date + "')";
        return ((Number) entityManager.createNativeQuery(queryString).getFirstResult()).intValue();

    }
}
