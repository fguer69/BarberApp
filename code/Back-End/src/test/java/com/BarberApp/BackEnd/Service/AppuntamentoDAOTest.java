package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import org.checkerframework.checker.units.qual.C;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppuntamentoDAOTest {
    @Mock
    private AppuntamentoRepository repository;
    @InjectMocks
    AppuntamentoDAO appuntamentoDAO;
    Cliente cliente = new Cliente();
    Dipendente dipendente = new Dipendente();
    Servizio servizio = new Servizio();
    Titolare titolare = new Titolare();
    Appuntamento appuntamento = new Appuntamento();

    @BeforeEach
    public void setUp(){
        cliente.setId(1);
        cliente.setNome("luca");
        cliente.setCognome("lambiase");
        cliente.setEmail("lukesesam94@gmail.com");
        cliente.setPassword("ciaociao");
        dipendente.setId(1);
        dipendente.setNome("Francesco");
        dipendente.setCognome("Dipendente");
        dipendente.setEmail("francescodipendente@gmail.com");
        dipendente.setPassword("ciaociao");
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
        appuntamento.setId(1);
        appuntamento.setDate(DateTime.now());
        appuntamento.setTime(DateTime.now());
        appuntamento.setCliente(cliente);
        appuntamento.setDipendente(dipendente);
        appuntamento.setServizio(servizio);
    }

    @Test
    @DisplayName("Test per verificare il salvataggio di un appuntamento")
    void saveAppuntamento(){
        appuntamentoDAO.saveAppointment(appuntamento);
        verify(repository).save(appuntamento);
    }

    @Test
    @DisplayName("Test per verificare che gli appuntamenti siano mostrati in ordine")
    void getAppuntamentiOrdered() {
        appuntamentoDAO.getAppuntamentiOrdered();
        verify(repository).findAllByOrderByDate();
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkAppuntamento di AppuntamentoDAO")
    void testCheckAppuntamentoPresente()
    {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        Appuntamento appuntamentoTemp = new Appuntamento();
        appuntamentoTemp.setDate(DateTime.now());
        when(repository.countAppointmentsByCliente_IdAndDate(cliente.getId(), appuntamentoTemp.getDate())).thenReturn(1);
        int result = appuntamentoDAO.checkAppuntamento(cliente, appuntamentoTemp);
        assertEquals(result, 1);
        verify(repository).countAppointmentsByCliente_IdAndDate(cliente.getId(), appuntamentoTemp.getDate());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo getAppuntamentiByCliente di AppuntamentoDAO")
    void testGetAppuntamentiByCliente()
    {
        Cliente cliente = new Cliente();
        cliente.setId(1);

        List<Appuntamento> appuntamenti = Arrays.asList(
                new Appuntamento(), new Appuntamento(), new Appuntamento()
        );

        when(repository.findByClienteIdOrderById(cliente.getId())).thenReturn(appuntamenti);
        List<Appuntamento> result = appuntamentoDAO.getAppuntamentiByCliente(cliente);
        assertEquals(appuntamenti, result);
        verify(repository).findByClienteIdOrderById(cliente.getId());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo getAppuntamentiByDipendente di AppuntamentoDAO")
    void testGetAppuntamentiByDipendente()
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setId(1);

        List<Appuntamento> appuntamenti = Arrays.asList(
                new Appuntamento(), new Appuntamento(), new Appuntamento()
        );

        when(repository.findByDipendenteId(dipendente.getId())).thenReturn(appuntamenti);
        List<Appuntamento> result = appuntamentoDAO.getAppuntamentiByDipendente(dipendenteTemp);
        assertEquals(appuntamenti, result);
        verify(repository).findByDipendenteId(dipendente.getId());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo getAppuntamentiOrdered di AppuntamentoDAO")
    void testGetAppuntamentiOrdered()
    {
        List<Appuntamento> appuntamenti = Arrays.asList(
                new Appuntamento(), new Appuntamento(), new Appuntamento()
        );

        when(repository.findAllByOrderByDate()).thenReturn(appuntamenti);
        List<Appuntamento> result = appuntamentoDAO.getAppuntamentiOrdered();
        assertEquals(appuntamenti, result);
        verify(repository).findAllByOrderByDate();
    }

}