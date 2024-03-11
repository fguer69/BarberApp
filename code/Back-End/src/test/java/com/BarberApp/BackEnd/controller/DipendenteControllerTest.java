package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = DipendenteController.class)
public class DipendenteControllerTest {

    Dipendente dipendente = new Dipendente();

    List<Dipendente> dipendenti = new ArrayList<>();

    @MockBean
    DipendenteDAO dipendenteDAO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        dipendente.setId(1);
        dipendente.setNome("luca");
        dipendente.setCognome("lambiase");
        dipendente.setEmail("lukesesam94@gmail.com");
        dipendente.setPassword("ciaociao");
        dipendenti.add(dipendente);
    }


    @Test
    @DisplayName("Salvataggio di un dipendente sul database la quale email è disponibile")
    public void saveDipendenteEmailDisponibile() throws Exception{
        when((dipendenteDAO).checkDipendente(dipendente.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
        verify(dipendenteDAO).saveDipendente(dipendente);
    }

    @Test
    @DisplayName("Salvataggio di un dipendente sul database la quale email è disponibile")
    public void saveDipendenteEmailIndisponibile() throws Exception{
        when((dipendenteDAO).checkDipendente(dipendente.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
        verify(dipendenteDAO, never()).saveDipendente(dipendente);
    }

    @Test
    @DisplayName("Elenco di tutti i dipendenti nel sistema")
    public void getAllDipendenti() throws Exception{
        Dipendente dipendente2 = new Dipendente();
        dipendente2.setId(2);
        dipendente2.setNome("Ivan");
        dipendente2.setCognome("Prota");
        dipendente2.setEmail("ivanprota@gmail.com");
        dipendente2.setPassword("ciaociao");
        dipendenti.add(dipendente2);
        when(dipendenteDAO.getEmployee()).thenReturn(dipendenti);
        mockMvc.perform(MockMvcRequestBuilders.get("/dipendenti/get-all").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(dipendenti.size())));
        dipendenteDAO.getEmployee();
    }

    @Test
    @DisplayName("Controllo della presenza di un email nel sistema")
    void testCheckEmailPresente() throws Exception
    {
        when(dipendenteDAO.checkDipendente(dipendente.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/dipendenti/check")
                .contentType(MediaType.APPLICATION_JSON).content(dipendente.getEmail()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
    }

    @Test
    @DisplayName("Controllo della presenza di un email nel sistema")
    void testCheckEmailAssente() throws Exception
    {
        when(dipendenteDAO.checkDipendente(dipendente.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/check")
                        .contentType(MediaType.APPLICATION_JSON).content(dipendente.getEmail()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del dipendente")
    void testUpdateDipendenteNonNullAndEmailDipendenteUgualeEmailDipendente1() throws Exception
    {
        when(dipendenteDAO.getDipendenteById(dipendente.getId())).thenReturn(Optional.of(dipendente));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/dipendenti/update")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(dipendenteDAO).getDipendenteById(dipendente.getId());
        verify(dipendenteDAO).updateEmployee(dipendente);
        verify(dipendenteDAO, never()).checkDipendente(dipendente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del dipendente")
    void testUpdateDipendenteNonNullAndEmailDipendenteDiversoEmailDipendente1AndEmailDisponibile() throws Exception
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setEmail("dipendentetemp@gmail.com");
        when(dipendenteDAO.getDipendenteById(dipendente.getId())).thenReturn(Optional.of(dipendenteTemp));
        when(dipendenteDAO.checkDipendente(dipendenteTemp.getEmail())).thenReturn(false);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("200", result.getResponse().getContentAsString());
        verify(dipendenteDAO).getDipendenteById(dipendente.getId());
        verify(dipendenteDAO).updateEmployee(dipendente);
        verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del dipendente")
    void testUpdateDipendenteNonNullAndEmailDipendenteDiversoEmailDipendente1AndEmailIndisponibile() throws Exception
    {
        Dipendente dipendenteTemp = new Dipendente();
        dipendenteTemp.setEmail("dipendentetemp@gmail.com");
        when(dipendenteDAO.getDipendenteById(dipendente.getId())).thenReturn(Optional.of(dipendenteTemp));
        when(dipendenteDAO.checkDipendente(dipendente.getEmail())).thenReturn(true);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("500", result.getResponse().getContentAsString());
        verify(dipendenteDAO).getDipendenteById(dipendente.getId());
        verify(dipendenteDAO, never()).updateEmployee(dipendente);
        verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
    }

    @Test
    @DisplayName("Aggiornamento delle informazioni del dipendente")
    void testUpdateDipendenteNull() throws Exception
    {
        when(dipendenteDAO.getDipendenteById(dipendente.getId())).thenReturn(Optional.empty());
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/update")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        assertEquals("501", result.getResponse().getContentAsString());
        verify(dipendenteDAO).getDipendenteById(dipendente.getId());
        verify(dipendenteDAO, never()).updateEmployee(dipendente);
        verify(dipendenteDAO, never()).checkDipendente(dipendente.getEmail());
    }

    @Test
    @DisplayName("Elenco di dipendenti disponibili in una certa data")
    /* Questo test fallisce perchè all'interno del metodo del controller
        c'è un'ulteriore costruzione degli oggetti date e time con i quali
        viene effettuata la chiamata ad un metodo della classe dao.
        Siccome non è possibile reperire in questo metodo di test gli stessi
        oggetti allocati all'interno del metodo del controller, il test fallisce
     */
    void testGetDipendentiByDate() throws Exception
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTime date = DateTime.parse("2024-03-07 11:00:00.000", formatter);
        DateTime time = DateTime.parse("2024-03-07 11:00:00.000", formatter);
        List<Dipendente> dipendenti = Arrays.asList(
                new Dipendente(), new Dipendente()
        );

        when(dipendenteDAO.getEmployeeByDate(date, time)).thenReturn(dipendenti);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/dipendenti/dipendentiDisponibili")
                .contentType(MediaType.APPLICATION_JSON).param("data", "2024-03-07 11:00:00.000").param("ora", "2024-03-07 11:00:00.000"))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        List<Dipendente> dipendentiTemp = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Dipendente>>() {
        });
        assertEquals(dipendentiTemp, dipendenti);
        verify(dipendenteDAO).getEmployeeByDate(date, time);
    }

    @Test
    @DisplayName("Login")
    void testLogin() throws Exception
    {
        when(dipendenteDAO.loginDipendente(dipendente.getEmail(), dipendente.getPassword())).thenReturn(Optional.of(dipendente));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/login")
                        .contentType(MediaType.APPLICATION_JSON).param("email", dipendente.getEmail()).param("password", dipendente.getPassword()))
                .andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Dipendente dipendenteTemp = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Dipendente>() {
        });
        assertEquals(dipendenteTemp, dipendente);
        verify(dipendenteDAO).loginDipendente(dipendente.getEmail(), dipendente.getPassword());
    }
}
