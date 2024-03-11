package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.BarberApp.BackEnd.model.titolare.TitolareDAO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = TitolareController.class)
public class TitolareControllerTest {
    Titolare titolare = new Titolare();

    List<Titolare> titolari = new ArrayList<>();

    @MockBean
    TitolareDAO titolareDAO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        titolare.setId(1);
        titolare.setNome("luca");
        titolare.setCognome("lambiase");
        titolare.setEmail("lukesesam94@gmail.com");
        titolare.setPassword("ciaociao");
        titolari.add(titolare);
    }

    @Test
    @DisplayName("Salvataggio di un titolare sul database la quale email è disponibile")
    /*
        Questo test fallisce perchè all'interno del metodo del controller
        avviene una modifica alla password del titolare e quindi il controllo
        verify viene effettuato su stesso metodo ma parametri diversi
     */
    public void saveTitolareEmailDisponibile() throws Exception{
        when(titolareDAO.checkTitolare(titolare.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(titolare)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(titolareDAO).checkTitolare(titolare.getEmail());
        verify(titolareDAO).saveTitolare(titolare);
    }

    @Test
    @DisplayName("Controllo se l'email del titolare è già presente nel sistema")
    void testCheckPassed() throws Exception
    {
        when(titolareDAO.checkTitolare(titolare.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/titolari/check")
                .contentType(MediaType.APPLICATION_JSON).content(titolare.getEmail()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(titolareDAO).checkTitolare(titolare.getEmail());
    }

    @Test
    @DisplayName("Controllo se l'email del titolare è già presente nel sistema")
    void testCheckFail() throws Exception
    {
        when(titolareDAO.checkTitolare(titolare.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/check")
                        .contentType(MediaType.APPLICATION_JSON).content(titolare.getEmail()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(titolareDAO).checkTitolare(titolare.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del titolare")
    void testUpdateTitolareNonNullAndEmailTitolareUgualeEmailTitolare1() throws Exception
    {
        when(titolareDAO.getTitolareById(titolare.getId())).thenReturn(Optional.of(titolare));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(titolare)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(titolareDAO).getTitolareById(titolare.getId());
        verify(titolareDAO).updateTitolare(titolare);
        verify(titolareDAO, never()).checkTitolare(titolare.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni di un titolare")
    void testUpdateTitolareNonNullAndEmailTitolareDiversaEmailTitolare1AndEmailDisponibile() throws Exception
    {
        Titolare titolareTemp = new Titolare();
        titolareTemp.setEmail("clientetemp@gmail.com");
        when(titolareDAO.getTitolareById(titolare.getId())).thenReturn(Optional.of(titolareTemp));
        when(titolareDAO.checkTitolare(titolare.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(titolare)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(titolareDAO).getTitolareById(titolare.getId());
        verify(titolareDAO).checkTitolare(titolare.getEmail());
        verify(titolareDAO).updateTitolare(titolare);
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni di un titolare")
    void testUpdateTitolareNonNullAndEmailTitolareDiversaEmailTitolare1AndEmailIndisponibile() throws Exception
    {
        Titolare titolareTemp = new Titolare();
        titolareTemp.setEmail("clientetemp@gmail.com");
        when(titolareDAO.getTitolareById(titolare.getId())).thenReturn(Optional.of(titolareTemp));
        when(titolareDAO.checkTitolare(titolare.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(titolare)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(titolareDAO).getTitolareById(titolare.getId());
        verify(titolareDAO).checkTitolare(titolare.getEmail());
        verify(titolareDAO, never()).updateTitolare(titolare);
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni di un titolare")
    void testUpdateTitolareNull() throws Exception
    {
        when(titolareDAO.getTitolareById(titolare.getId())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(titolare)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("501", result.getResponse().getContentAsString());
        verify(titolareDAO, never()).updateTitolare(titolare);
        verify(titolareDAO, never()).checkTitolare(titolare.getEmail());
    }

    @Test
    @DisplayName("Login")
    void testLogin() throws Exception
    {
        when(titolareDAO.loginTitolare(titolare.getEmail(), titolare.getPassword())).thenReturn(Optional.of(titolare));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/login")
                        .contentType(MediaType.APPLICATION_JSON).param("email", titolare.getEmail()).param("password", titolare.getPassword()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Titolare titolareTemp = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Titolare>() {
        });
        assertEquals(titolareTemp, titolare);
        verify(titolareDAO).loginTitolare(titolare.getEmail(), titolare.getPassword());
    }
}
