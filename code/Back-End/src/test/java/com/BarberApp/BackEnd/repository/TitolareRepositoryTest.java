package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class TitolareRepositoryTest {

    @Autowired
    private TitolareRepository repository;
    Titolare titolare = new Titolare();
    @BeforeEach
    public void setUp(){
        titolare.setId(1);
        titolare.setEmail("titolareuno@gmail.com");
        titolare.setPassword("ciaociao");
        titolare.setNome("titolareuno");
        titolare.setCognome("uno");
    }

    @Test
    public void saveTitolare(){
        Titolare savedTitolare = repository.save(titolare);
        if(savedTitolare.getId() == titolare.getId())
            System.out.println("PASSED");
        else
            System.out.println("FAILED");
    }
}
