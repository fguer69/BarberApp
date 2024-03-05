package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.j2objc.annotations.AutoreleasePool;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(controllers = ClientsController.class)
public class ClienteControllerTest {

    Cliente cliente = new Cliente();

    List<Cliente> clienti = new ArrayList<>();

    @MockBean
    private ClienteDAO clienteDAO;

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
    @DisplayName("Salvataggio di un cliente sul database la quale email è disponibile")
    public void saveClienteEmailDisponibile() throws Exception{
       /* when((clienteDAO).checkCliente(cliente.getEmail())).thenReturn(false);
        doAnswer(invocation -> {
            System.out.println("CLIENTE SALVATO CON SUCCESSO");
            return null;
        }).when(clienteDAO).saveCliente(cliente);*/
        when((clienteDAO).checkCliente(cliente.getEmail())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/clienti/save")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200));
        /*verify(clienteDAO).checkCliente(cliente.getEmail());
        verify(clienteDAO).saveCliente(cliente);*/
    }

    @Test
    @DisplayName("Elenco di tutti i clienti nel sistema")
    public void getAllClienti() throws Exception{
        Cliente cliente2 = new Cliente();
        cliente2.setId(2);
        cliente2.setNome("Ivan");
        cliente2.setCognome("Prota");
        cliente2.setEmail("ivanprota@gmail.com");
        cliente2.setPassword("ciaociao");
        clienti.add(cliente2);
        when(clienteDAO.getAllClienti()).thenReturn(clienti);
        mockMvc.perform(MockMvcRequestBuilders.get("/clienti/get-all").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(clienti.size())));
    }
}
