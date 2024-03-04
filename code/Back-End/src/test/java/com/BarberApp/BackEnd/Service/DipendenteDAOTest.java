package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DipendenteDAOTest {
    @Mock
    private DipendenteRepository repository;
    @InjectMocks
    DipendenteDAO dipendenteDAO;
    Dipendente dipendente = new Dipendente();
    @BeforeEach
    public void setUp(){
        dipendente.setId(1);
        dipendente.setNome("luca");
        dipendente.setCognome("lambiase");
        dipendente.setEmail("lukesesam94@gmail.com");
        dipendente.setPassword("ciaociao");
    }

    @Test
    @DisplayName("Test per verificare il salvataggio di un dipendente")
    void saveDipendente(){
        dipendenteDAO.saveDipendente(dipendente);
        verify(repository).save(dipendente);
    }

}
