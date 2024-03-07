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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkTitolare della classe TitolareDAO")
    void testCheckTitolarePresente()
    {
        Titolare titolareTemp = new Titolare();
        titolareTemp.setEmail("titolaretemp@gmail.com");
        when(repository.getTitolareByEmail(titolareTemp.getEmail())).thenReturn(titolare);
        boolean result = titolareDAO.checkTitolare(titolareTemp.getEmail());
        assertTrue(result);
        verify(repository).getTitolareByEmail(titolareTemp.getEmail());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkTitolare della classe TitolareDAO")
    void testCheckTitolareAssente()
    {
        Titolare titolareTemp = new Titolare();
        titolareTemp.setEmail("titolaretemp@gmail.com");
        when(repository.getTitolareByEmail(titolareTemp.getEmail())).thenReturn(null);
        boolean result = titolareDAO.checkTitolare(titolareTemp.getEmail());
        assertFalse(result);
        verify(repository).getTitolareByEmail(titolareTemp.getEmail());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo loginTitolare della classe TitolareDAO")
    void testLoginTitolarePresente()
    {
        Titolare titolareTemp = new Titolare();
        titolareTemp.setEmail("titolaretemp@gmail.com");
        titolareTemp.setPassword("password");
        titolareTemp.setNome("Titolare");
        titolareTemp.setCognome("Temp");

        when(repository.getTitolareByEmailAndPassword(titolareTemp.getEmail(), titolareTemp.getPassword())).thenReturn(Optional.of(titolareTemp));
        Optional<Titolare> result = titolareDAO.loginTitolare(titolareTemp.getEmail(), titolareTemp.getPassword());
        assertTrue(result.isPresent());
        assertEquals(titolareTemp, result.get());
        verify(repository).getTitolareByEmailAndPassword(titolareTemp.getEmail(), titolareTemp.getPassword());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo loginTitolare della classe TitolareDAO")
    void testLoginTitolareAssente()
    {
        Titolare titolareTemp = new Titolare();
        titolareTemp.setEmail("titolaretemp@gmail.com");
        titolareTemp.setPassword("password");
        titolareTemp.setNome("Titolare");
        titolareTemp.setCognome("Temp");

        when(repository.getTitolareByEmailAndPassword(titolareTemp.getEmail(), titolareTemp.getPassword())).thenReturn(Optional.empty());
        Optional<Titolare> result = titolareDAO.loginTitolare(titolareTemp.getEmail(), titolareTemp.getPassword());
        assertTrue(result.isEmpty());
        verify(repository).getTitolareByEmailAndPassword(titolareTemp.getEmail(), titolareTemp.getPassword());
    }
}
