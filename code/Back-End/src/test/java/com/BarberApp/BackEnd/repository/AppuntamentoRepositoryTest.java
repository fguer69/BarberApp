package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.servizio.ServizioRepository;
import com.BarberApp.BackEnd.model.titolare.TitolareRepository;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.MatcherAssert;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppuntamentoRepositoryTest {

    @Autowired
    private AppuntamentoRepository repository;
    @Autowired
    private TitolareRepository titolareRepository;
    @Autowired
    private ServizioRepository servizioRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    Cliente cliente = new Cliente();
    Dipendente dipendente = new Dipendente();
    Servizio servizio = new Servizio();
    Titolare titolare = new Titolare();
    Appuntamento appuntamento = new Appuntamento();
    @BeforeAll
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
        clienteRepository.save(cliente);
        dipendenteRepository.save(dipendente);
        titolareRepository.save(titolare);
        servizioRepository.save(servizio);
        repository.save(appuntamento);
    }

    @Test
    public void testcountAppointmentsByCliente_IdAndDate()
    {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

        List<Cliente> clienti = clienteRepository.findAll();
        int id = clienti.getFirst().getId();
        int count = repository.countAppointmentsByCliente_IdAndDate(id, appuntamento.getDate());
        assertEquals(count, 1);
    }

    @Test
    public void testFindByClienteIdOrderById()
    {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

        Appuntamento appuntamentoTemp1 = new Appuntamento();
        appuntamentoTemp1.setId(2);
        appuntamentoTemp1.setDate(DateTime.parse("2024-03-10 10:00:00.000", dateFormatter));
        appuntamentoTemp1.setTime(DateTime.parse("2024-03-10 10:00:00.000", dateFormatter));
        appuntamentoTemp1.setCliente(cliente);
        appuntamentoTemp1.setDipendente(dipendente);
        appuntamentoTemp1.setServizio(servizio);
        repository.save(appuntamentoTemp1);

        Appuntamento appuntamentoTemp2 = new Appuntamento();
        appuntamentoTemp2.setId(3);
        appuntamentoTemp2.setDate(DateTime.parse("2024-03-15 12:00:00.000", dateFormatter));
        appuntamentoTemp2.setTime(DateTime.parse("2024-03-15 10:00:00.000", dateFormatter));
        appuntamentoTemp2.setCliente(cliente);
        appuntamentoTemp2.setDipendente(dipendente);
        appuntamentoTemp2.setServizio(servizio);
        repository.save(appuntamentoTemp2);

        List<Cliente> clienti = clienteRepository.findAll();
        int id = clienti.getFirst().getId();

        List<Appuntamento> appuntamenti = repository.findByClienteIdOrderById(id);

        int result = 0;
        for (int i = 0; i < appuntamenti.size()-1; i++)
        {
            for (int j = i + 1; j < appuntamenti.size(); j++) {
                Appuntamento appuntamento1 = appuntamenti.get(i);
                Appuntamento appuntamento2 = appuntamenti.get(j);
                long millis1 = appuntamento1.getDate().getMillis();
                long millis2 = appuntamento2.getDate().getMillis();
                if (millis1 < millis2)
                    result = 1;
            }
        }

        assertEquals(result, 1);
    }

    @Test
    public void testFindByDipendenteId()
    {
        int id = dipendenteRepository.findAll().getFirst().getId();
        List<Appuntamento> appuntamenti = repository.findByDipendenteId(id);
        List<Integer> idDipendenteAppuntamento = new ArrayList<>();
        for(Appuntamento a : appuntamenti)
            idDipendenteAppuntamento.add(a.getDipendente().getId());
        MatcherAssert.assertThat(idDipendenteAppuntamento,allOf(hasItem(id)));
    }

    @Test
    public void testFindAllByOrderByDate()
    {
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

        Appuntamento appuntamentoTemp1 = new Appuntamento();
        appuntamentoTemp1.setId(2);
        appuntamentoTemp1.setDate(DateTime.parse("2024-03-10 10:00:00.000", dateFormatter));
        appuntamentoTemp1.setTime(DateTime.parse("2024-03-10 10:00:00.000", dateFormatter));
        appuntamentoTemp1.setCliente(cliente);
        appuntamentoTemp1.setDipendente(dipendente);
        appuntamentoTemp1.setServizio(servizio);
        repository.save(appuntamentoTemp1);

        Appuntamento appuntamentoTemp2 = new Appuntamento();
        appuntamentoTemp2.setId(3);
        appuntamentoTemp2.setDate(DateTime.parse("2024-03-15 12:00:00.000", dateFormatter));
        appuntamentoTemp2.setTime(DateTime.parse("2024-03-15 10:00:00.000", dateFormatter));
        appuntamentoTemp2.setCliente(cliente);
        appuntamentoTemp2.setDipendente(dipendente);
        appuntamentoTemp2.setServizio(servizio);
        repository.save(appuntamentoTemp2);

        List<Appuntamento> appuntamenti = repository.findAllByOrderByDate();

        int result = 0;
        for (int i = 0; i < appuntamenti.size()-1; i++)
        {
            for (int j = i + 1; j < appuntamenti.size(); j++) {
                Appuntamento appuntamento1 = appuntamenti.get(i);
                Appuntamento appuntamento2 = appuntamenti.get(j);
                long millis1 = appuntamento1.getDate().getMillis();
                long millis2 = appuntamento2.getDate().getMillis();
                if (millis1 < millis2)
                    result = 1;
            }
        }

        assertEquals(result, 1);
    }
}
