package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkDipendente della classe DipendenteDAO")
    void testCheckDipendentePresente()
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setEmail("dipendentetemp@gmail.com");

        when(repository.findByEmail(dipendenteTemp.getEmail())).thenReturn(dipendenteTemp);
        boolean result = dipendenteDAO.checkDipendente(dipendenteTemp.getEmail());
        assertTrue(result);
        verify(repository).findByEmail(dipendenteTemp.getEmail());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkDipendente della classe DipendenteDAO")
    void testCheckDipendenteAssente()
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setEmail("dipendentetemp@gmail.com");

        when(repository.findByEmail(dipendenteTemp.getEmail())).thenReturn(null);
        boolean result = dipendenteDAO.checkDipendente(dipendenteTemp.getEmail());
        assertFalse(result);
        verify(repository).findByEmail(dipendenteTemp.getEmail());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo getEmployeeByDate della classe DipendenteDAO")
    void testGetEmployeeByDate()
    {
        List<Dipendente> dipendenti = Arrays.asList(
                new Dipendente(), new Dipendente(), new Dipendente()
        );

        DateTime date = DateTime.now();
        DateTime time = DateTime.now();

        when(repository.findAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime(date, time)).thenReturn(dipendenti);
        List<Dipendente> result = dipendenteDAO.getEmployeeByDate(date, time);
        assertEquals(dipendenti, result);
        verify(repository).findAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime(date, time);
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo loginDipendente della classe DipendenteDAO")
    void testLoginDipendentePresente()
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setEmail("dipendentetemp@gmail.com");
        dipendenteTemp.setPassword("password");
        dipendenteTemp.setNome("dipendente");
        dipendenteTemp.setCognome("temp");

        when(repository.getDipendenteByEmailAndPassword(dipendenteTemp.getEmail(), dipendenteTemp.getPassword())).thenReturn(Optional.of(dipendenteTemp));
        Optional<Dipendente> result = dipendenteDAO.loginDipendente(dipendenteTemp.getEmail(), dipendenteTemp.getPassword());
        assertTrue(result.isPresent());
        assertEquals(dipendenteTemp, result.get());
        verify(repository).getDipendenteByEmailAndPassword(dipendenteTemp.getEmail(), dipendenteTemp.getPassword());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo loginDipendente della classe DipendenteDAO")
    void testLoginDipendenteAssente()
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setEmail("dipendentetemp@gmail.com");
        dipendenteTemp.setPassword("password");
        dipendenteTemp.setNome("dipendente");
        dipendenteTemp.setCognome("temp");

        when(repository.getDipendenteByEmailAndPassword(dipendenteTemp.getEmail(), dipendenteTemp.getPassword())).thenReturn(Optional.empty());
        Optional<Dipendente> result = dipendenteDAO.loginDipendente(dipendenteTemp.getEmail(), dipendenteTemp.getPassword());
        assertFalse(result.isPresent());
        verify(repository).getDipendenteByEmailAndPassword(dipendenteTemp.getEmail(), dipendenteTemp.getPassword());
    }

}
