package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.cliente.ClienteDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.j2objc.annotations.AutoreleasePool;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;



@WebMvcTest(controllers = ClientsController.class)
public class ClienteControllerTest {

    Cliente cliente = new Cliente();

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
    }


    @Test
    @DisplayName("Salvataggio di un cliente sul database")
    public void saveCliente() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/clienti/save")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cliente)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
