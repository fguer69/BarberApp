package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class DipendenteRepositoryTest {

    @Autowired
    private DipendenteRepository repository;
    Dipendente dipendente = new Dipendente();
    @BeforeEach
    public void setUp(){
        dipendente.setId(1);
        dipendente.setNome("Francesco");
        dipendente.setCognome("Dipendente");
        dipendente.setEmail("francescodipendente@gmail.com");
        dipendente.setPassword("ciaociao");
    }

    @Test
    public void saveDipendente(){
        Dipendente savedDipendente = repository.save(dipendente);
        if(savedDipendente.getId() == dipendente.getId())
            System.out.println("PASSED");
        else
            System.out.println("FAILED");
    }
}
