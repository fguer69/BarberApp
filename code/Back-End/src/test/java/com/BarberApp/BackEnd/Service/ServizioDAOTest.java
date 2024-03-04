package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.servizio.ServizioDAO;
import com.BarberApp.BackEnd.model.servizio.ServizioRepository;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareDAO;
import com.BarberApp.BackEnd.model.titolare.TitolareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServizioDAOTest {
    @Mock
    private ServizioRepository repository;
    @InjectMocks
    ServizioDAO servizioDAO;
    Servizio servizio = new Servizio();
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
        servizio.setTitolare(titolare);
    }

    @Test
    @DisplayName("Test per verificare il salvataggio di un servizio")
    void saveServizio(){
        servizioDAO.saveService(servizio);
        verify(repository).save(servizio);
    }

}
