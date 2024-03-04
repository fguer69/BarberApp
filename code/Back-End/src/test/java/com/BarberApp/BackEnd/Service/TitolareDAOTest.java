package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
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
public class TitolareDAOTest {
    @Mock
    private TitolareRepository repository;
    @InjectMocks
    TitolareDAO titolareDAO;
    Titolare titolare = new Titolare();
    @BeforeEach
    public void setUp(){
        titolare.setId(1);
        titolare.setNome("luca");
        titolare.setCognome("lambiase");
        titolare.setEmail("lukesesam94@gmail.com");
        titolare.setPassword("ciaociao");
    }

    @Test
    @DisplayName("Test per verificare il salvataggio di un titolare")
    void saveTitolare(){
        titolareDAO.saveTitolare(titolare);
        verify(repository).save(titolare);
    }

}
