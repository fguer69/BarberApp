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
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.BarberApp.BackEnd.model.titolare.Titolare;

@DataJpaTest
@ActiveProfiles("test")
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
        clienteRepository.save(cliente);
        dipendenteRepository.save(dipendente);
        titolareRepository.save(titolare);
        servizioRepository.save(servizio);
    }

    @Test
    public void saveAppuntamento(){
        Appuntamento savedAppuntamento = repository.save(appuntamento);
        if(appuntamento != null)
            System.out.println("PASSED");
        else
            System.out.println("FAILED");
    }
}
