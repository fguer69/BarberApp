package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.dipendente.DipendenteDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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
        doAnswer(invocation -> {
            System.out.println("DIPENDENTE SALVATO CON SUCCESSO");
            return null;
        }).when(dipendenteDAO).saveDipendente(dipendente);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/dipendenti/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dipendente)))
                .andExpect(MockMvcResultMatchers.status().is(200));
        if(!dipendenteDAO.checkDipendente(dipendente.getEmail())){
            dipendenteDAO.saveDipendente(dipendente);
        }
        InOrder inorder = inOrder(dipendenteDAO);
        inorder.verify(dipendenteDAO).checkDipendente(dipendente.getEmail());
        inorder.verify(dipendenteDAO).saveDipendente(dipendente);
        //verify(clienteDAO).checkCliente(cliente.getEmail());
        //verify(clienteDAO).saveCliente(cliente);
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



}
