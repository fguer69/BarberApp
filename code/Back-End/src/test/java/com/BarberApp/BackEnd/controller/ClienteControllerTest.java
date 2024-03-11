package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.MailService.EmailServiceImpl;
import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.j2objc.annotations.AutoreleasePool;
import org.checkerframework.checker.units.qual.C;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = ClientsController.class)
public class ClienteControllerTest {

    Cliente cliente = new Cliente();

    List<Cliente> clienti = new ArrayList<>();

    @MockBean
    private ClienteDAO clienteDAO;

    @MockBean
    private EmailServiceImpl emailService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        cliente.setId(1);
        cliente.setNome("luca");
        cliente.setCognome("lambiase");
        cliente.setEmail("lukesesam94@gmail.com");
        cliente.setPassword("ciaociao");
        clienti.add(cliente);
    }

    @Test
    @DisplayName("Salvataggio di un cliente")
    void testSaveFallita() throws Exception
    {
        when(clienteDAO.checkCliente(cliente.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(clienteDAO).checkCliente(cliente.getEmail());
        verify(clienteDAO, never()).saveCliente(cliente);
    }

    @Test
    @DisplayName("Salvataggio di un cliente")
    void testSaveSuccesso() throws Exception
    {
        when(clienteDAO.checkCliente(cliente.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(clienteDAO).checkCliente(cliente.getEmail());
        verify(clienteDAO).saveCliente(cliente);
    }

    @Test
    @DisplayName("Verifica della presenza di un email nel sistema")
    void testCheckSuccesso() throws Exception
    {
        when(clienteDAO.checkCliente(cliente.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/cliente/check")
                        .contentType(MediaType.APPLICATION_JSON).content(cliente.getEmail()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(clienteDAO).checkCliente(cliente.getEmail());
    }

    @Test
    @DisplayName("Verifica della presenza di un email nel sistema")
    void testCheckFallita() throws Exception
    {
        when(clienteDAO.checkCliente(cliente.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/cliente/check")
                        .contentType(MediaType.APPLICATION_JSON).content(cliente.getEmail()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(clienteDAO).checkCliente(cliente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del cliente")
    void testUpdateClienteNonNullAndEmailClienteUgualeEmailCliente1() throws Exception
    {
        when(clienteDAO.getClienteById(cliente.getId())).thenReturn(Optional.of(cliente));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(clienteDAO).getClienteById(cliente.getId());
        verify(clienteDAO).updateClient(cliente);
        verify(clienteDAO, never()).checkCliente(cliente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del cliente")
    void testUpdateClienteNonNullAndEmailClienteDiversoEmailCliente1AndEmailDisponibile() throws Exception
    {
        Cliente clienteTemp = new Cliente();
        clienteTemp.setEmail("clientetemp@gmail.com");
        when(clienteDAO.getClienteById(cliente.getId())).thenReturn(Optional.of(clienteTemp));
        when(clienteDAO.checkCliente(clienteTemp.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(clienteDAO).getClienteById(cliente.getId());
        verify(clienteDAO).updateClient(cliente);
        verify(clienteDAO).checkCliente(cliente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del cliente")
    void testUpdateClienteNonNullAndEmailClienteDiversoEmailCliente1AndEmailIndisponibile() throws Exception
    {
        Cliente clienteTemp = new Cliente();
        clienteTemp.setEmail("clientetemp@gmail.com");
        when(clienteDAO.getClienteById(cliente.getId())).thenReturn(Optional.of(clienteTemp));
        when(clienteDAO.checkCliente(cliente.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(clienteDAO).getClienteById(cliente.getId());
        verify(clienteDAO, never()).updateClient(cliente);
        verify(clienteDAO).checkCliente(cliente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del cliente")
    void testUpdateClienteNull() throws Exception
    {
        when(clienteDAO.getClienteById(cliente.getId())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("501", result.getResponse().getContentAsString());
        verify(clienteDAO).getClienteById(cliente.getId());
        verify(clienteDAO, never()).updateClient(cliente);
        verify(clienteDAO, never()).checkCliente(cliente.getEmail());
    }

    @Test
    @DisplayName("Login")
    void testLogin() throws Exception
    {
        when(clienteDAO.loginCliente(cliente.getEmail(), cliente.getPassword())).thenReturn(Optional.of(cliente));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/clienti/login")
                        .contentType(MediaType.APPLICATION_JSON).param("email", cliente.getEmail()).param("password", cliente.getPassword()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Cliente clienteTemp = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Cliente>() {
        });
        assertEquals(clienteTemp, cliente);
        verify(clienteDAO).loginCliente(cliente.getEmail(), cliente.getPassword());
    }
}
