package com.BarberApp.BackEnd.model.OrariServizio;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrariServizioRepository extends ListCrudRepository<OrariServizio, Integer> {

    @Query(value = "SELECT * FROM orari_servizio WHERE salone_id = ?1 ORDER BY Start_hour", nativeQuery = true)
    List<OrariServizio> findBySalone(Long Salone);


}
