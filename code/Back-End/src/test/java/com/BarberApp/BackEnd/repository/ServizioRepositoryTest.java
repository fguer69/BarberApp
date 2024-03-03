package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.servizio.ServizioRepository;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class ServizioRepositoryTest {

    @Autowired
    private ServizioRepository repository;
    Servizio servizio = new Servizio();

    @Autowired
    private TitolareRepository TitolareRepository;
    Titolare titolare = new Titolare();
    @BeforeEach
    public void setUp(){
        servizio.setId(1);
        servizio.setTipo("Taglio");
        servizio.setAssetImage("asset/image/taglio.jpg");
        servizio.setCosto(3.0);
        titolare.setId(1);
        titolare.setEmail("titolareuno@gmail.com");
        titolare.setPassword("ciaociao");
        titolare.setNome("titolareuno");
        titolare.setCognome("uno");
        TitolareRepository.save(titolare);
    }

    @Test
    public void saveServizio(){
        servizio.setTitolare(titolare);
        Servizio savedServizio = repository.save(servizio);
        if(savedServizio.getId() == servizio.getId())
            System.out.println("PASSED");
        else
            System.out.println("FAILED");
    }
}
