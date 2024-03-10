package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.servizio.ServizioDAO;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.BarberApp.BackEnd.model.titolare.TitolareDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = ServizioController.class)
public class ServizioControllerTest {
    Servizio servizio = new Servizio();

    Titolare titolare = new Titolare();

    List<Servizio> servizi = new ArrayList<>();

    @MockBean
    ServizioDAO servizioDAO;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        servizio.setId(1);
        servizio.setTipo("Taglio");
        servizio.setAssetImage("asset/image/Taglio.jpg");
        servizio.setCosto(3.50);
        titolare.setId(1);
        titolare.setEmail("titolareuno@gmail.com");
        titolare.setPassword("ciaociao");
        titolare.setNome("titolareuno");
        titolare.setCognome("uno");
        servizio.setTitolare(titolare);
        servizi.add(servizio);

    }

    @Test
    @DisplayName("Salvataggio di un servizio sul database")
    public void saveServizioTest()throws Exception{
        doAnswer(invocation -> {
            System.out.println("SERVIZIO SALVATO CON SUCCESSO");
            return null;
        }).when(servizioDAO).saveService(servizio);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/servizi/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servizio)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        servizioDAO.saveService(servizio);
        verify(servizioDAO).saveService(servizio);
    }
    @Test
    @DisplayName("Elenco di tutti i servizi sul database")
    public void getAllServices() throws Exception {
        Servizio servizio2 = new Servizio();
        servizio2.setId(2);
        servizio2.setTipo("Shampoo");
        servizio2.setAssetImage("asset/image/Shampoo.jpg");
        servizio2.setCosto(4.0);
        servizio2.setTitolare(titolare);
        servizi.add(servizio2);
        when(servizioDAO.getAll()).thenReturn(servizi);
        mockMvc.perform(MockMvcRequestBuilders.get("/servizi/get-all").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
        List<Servizio> result = servizioDAO.getAll();
        assertEquals(servizi.size(),result.size());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del servizio")
    void testUpdateServiceSuccesso() throws Exception
    {
        when(servizioDAO.serviceUpdate(any())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/servizi/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servizio)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(servizioDAO).serviceUpdate(any());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del servizio")
    void testUpdateServiceFallito() throws Exception
    {
        when(servizioDAO.serviceUpdate(any())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/servizi/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(servizio)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("501", result.getResponse().getContentAsString());
        verify(servizioDAO).serviceUpdate(any());
    }
}
