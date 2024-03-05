package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;
    Cliente cliente = new Cliente();
    @BeforeEach
    public void setUp(){
        cliente.setId(1);
        cliente.setNome("luca");
        cliente.setCognome("lambiase");
        cliente.setEmail("lucalambiase@gmail.com");
        cliente.setPassword("ciaociao");
        repository.save(cliente);
    }

    @Test
    public void testGetClienteByEmail()
    {
        Cliente cliente1 = repository.getClienteByEmail(cliente.getEmail());
        assertEquals(cliente, cliente1);
    }

    @Test
    public void testGetClienteByEmailAndPassword()
    {
        Optional<Cliente> optionalCliente = repository.getClienteByEmailAndPassword(cliente.getEmail(), cliente.getPassword());
        Cliente cliente1 = null;
        if (optionalCliente.isPresent())
            cliente1 = optionalCliente.get();
        assertEquals(cliente, cliente1);
    }
}
