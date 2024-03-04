package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

@ExtendWith(MockitoExtension.class)
public class ClienteDAOTest {
    @Mock
    private ClienteRepository repository;
    @InjectMocks
    ClienteDAO clienteDAO;
    @Mock
    Cliente cliente = new Cliente();
    @BeforeEach
    public void setUp(){
        cliente.setId(1);
        cliente.setNome("luca");
        cliente.setCognome("lambiase");
        cliente.setEmail("lukesesam94@gmail.com");
        cliente.setPassword("ciaociao");
    }

    @Test
    @DisplayName("Test per verificare il salvataggio di un cliente")
    void saveCliente(){
        boolean passed = false;
        clienteDAO.saveCliente(cliente);
        List<Cliente> clienti = clienteDAO.getAllClienti();
        for(Cliente c : clienti){
            if(cliente.getEmail().equals(c.getEmail()))
                passed = true;
            System.out.println(clienti);
        }
        System.out.println(passed);
    }

}
