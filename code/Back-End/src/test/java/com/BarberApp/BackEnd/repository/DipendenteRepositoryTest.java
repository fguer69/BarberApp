package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteRepository;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.servizio.ServizioRepository;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareRepository;
import com.vaadin.open.App;
import org.checkerframework.checker.units.qual.A;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DipendenteRepositoryTest {

    @Autowired
    private DipendenteRepository repository;
    Dipendente dipendente = new Dipendente();

    @Autowired
    private AppuntamentoRepository appuntamentoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private TitolareRepository titolareRepository;
    @Autowired
    private ServizioRepository servizioRepository;
    @BeforeAll
    public void setUp(){
        dipendente.setId(1);
        dipendente.setNome("Francesco");
        dipendente.setCognome("Dipendente");
        dipendente.setEmail("francescodipendente@gmail.com");
        dipendente.setPassword("ciaociao");
        repository.save(dipendente);
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento di findByEmail di DipendenteRepository")
    public void testFindByEmail()
    {
        Dipendente dipendente1 = repository.findByEmail(dipendente.getEmail());
        assertEquals(dipendente1, dipendente);
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento di findAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime di DipendenteRepository")
    public void testFindAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime()
    {
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNome("luca");
        cliente.setCognome("lambiase");
        cliente.setEmail("lukesesam94@gmail.com");
        cliente.setPassword("ciaociao");

        Servizio servizio = new Servizio();
        servizio.setId(1);
        servizio.setTipo("Taglio");
        servizio.setAssetImage("asset/image/taglio.jpg");
        servizio.setCosto(3.0);

        Titolare titolare = new Titolare();
        titolare.setId(1);
        titolare.setEmail("titolareuno@gmail.com");
        titolare.setPassword("ciaociao");
        titolare.setNome("titolareuno");
        titolare.setCognome("uno");
        servizio.setTitolare(titolare);

        Appuntamento appuntamento = new Appuntamento();
        appuntamento.setId(1);
        appuntamento.setDate(DateTime.now());
        appuntamento.setTime(DateTime.now());
        appuntamento.setCliente(cliente);
        appuntamento.setDipendente(dipendente);
        appuntamento.setServizio(servizio);
        clienteRepository.save(cliente);
        titolareRepository.save(titolare);
        servizioRepository.save(servizio);
        appuntamentoRepository.save(appuntamento);

        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTime date = DateTime.parse("2024-03-25 10:00:00.000", dateFormatter);
        DateTime time = DateTime.parse("2024-03-25 10:00:00.000", dateFormatter);

        List<Dipendente> dipendenti = repository.findAvailableEmployeesByAppuntamentiDateAndAppuntamentiTime(date, time);
        assertEquals(dipendente, dipendenti.getFirst());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento di getDipendenteByEmailAndPassword di DipendenteRepository")
    public void testGetDipendenteByEmailAndPassword()
    {
        Optional<Dipendente> optionalDipendente = repository.getDipendenteByEmailAndPassword(dipendente.getEmail(), dipendente.getPassword());
        Dipendente dipendente1 = null;
        if (optionalDipendente.isPresent())
            dipendente1 = optionalDipendente.get();

        assertEquals(dipendente, dipendente1);
    }
}
