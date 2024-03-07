package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.cliente.ClienteRepository;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteDAOTest {
    @Mock
    private ClienteRepository repository;
    @InjectMocks
    ClienteDAO clienteDAO;
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
        clienteDAO.saveCliente(cliente);
        verify(repository).save(cliente);
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkCliente di ClienteDAO")
    void testCheckClientePresente()
    {
        Cliente clienteTemp = new Cliente();
        clienteTemp.setEmail("clienteTemp@gmail.com");

        when(repository.getClienteByEmail(clienteTemp.getEmail())).thenReturn(clienteTemp);
        boolean result = clienteDAO.checkCliente(clienteTemp.getEmail());
        assertTrue(result);
        verify(repository).getClienteByEmail(clienteTemp.getEmail());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo checkCliente di ClienteDAO")
    void testCheckClienteAssente()
    {
        Cliente clienteTemp = new Cliente();
        clienteTemp.setEmail("clienteTemp@gmail.com");

        when(repository.getClienteByEmail(clienteTemp.getEmail())).thenReturn(null);
        boolean result = clienteDAO.checkCliente(clienteTemp.getEmail());
        assertFalse(result);
        verify(repository).getClienteByEmail(clienteTemp.getEmail());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo loginCliente di ClienteDAO")
    void testLoginClientePresente()
    {
        Cliente clienteTemp = new Cliente();
        clienteTemp.setEmail("clienteTemp@gmail.com");
        clienteTemp.setPassword("password");
        clienteTemp.setNome("Cliente");
        clienteTemp.setCognome("Temp");

        when(repository.getClienteByEmailAndPassword(clienteTemp.getEmail(), clienteTemp.getPassword())).thenReturn(Optional.of(clienteTemp));
        Optional<Cliente> result = clienteDAO.loginCliente(clienteTemp.getEmail(), clienteTemp.getPassword());
        assertTrue(result.isPresent());
        assertEquals(clienteTemp, result.get());
        verify(repository).getClienteByEmailAndPassword(clienteTemp.getEmail(), clienteTemp.getPassword());
    }

    @Test
    @DisplayName("Test per verificare il corretto funzionamento del metodo loginCliente di ClienteDAO")
    void testLoginClienteAssente()
    {
        Cliente clienteTemp = new Cliente();
        clienteTemp.setEmail("clienteTemp@gmail.com");
        clienteTemp.setPassword("password");
        clienteTemp.setNome("Cliente");
        clienteTemp.setCognome("Temp");

        when(repository.getClienteByEmailAndPassword(clienteTemp.getEmail(), clienteTemp.getPassword())).thenReturn(Optional.empty());
        Optional<Cliente> result = clienteDAO.loginCliente(clienteTemp.getEmail(), clienteTemp.getPassword());
        assertFalse(result.isPresent());
        verify(repository).getClienteByEmailAndPassword(clienteTemp.getEmail(), clienteTemp.getPassword());
    }
}
