package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.repository.AppuntamentoRepositoryTest;
import jakarta.inject.Inject;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

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
}