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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


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
        repository.save(titolare);
    }

    @Test
    public void testgetTitolareByEmail()
    {
        Titolare titolare1 = repository.getTitolareByEmail(titolare.getEmail());
        assertEquals(titolare, titolare1);
    }

    @Test
    public void testGetTitolareByEmailAndPassword()
    {
        Optional<Titolare> optionalTitolare = repository.getTitolareByEmailAndPassword(titolare.getEmail(), titolare.getPassword());
        Titolare titolare1 = null;
        if (optionalTitolare.isPresent())
            titolare1 = optionalTitolare.get();
        assertEquals(titolare, titolare1);
    }
}
