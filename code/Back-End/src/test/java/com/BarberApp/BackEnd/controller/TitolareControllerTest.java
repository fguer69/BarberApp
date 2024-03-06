package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.titolare.Titolare;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

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
    public void saveTitolareEmailDisponibile() throws Exception{
        when((titolareDAO).checkTitolare(titolare.getEmail())).thenReturn(false);
        doAnswer(invocation -> {
            System.out.println("TITOLARE SALVATO CON SUCCESSO");
            return null;
        }).when(titolareDAO).saveTitolare(titolare);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/titolari/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(titolare)))
                .andExpect(MockMvcResultMatchers.status().is(200));
        if(!titolareDAO.checkTitolare(titolare.getEmail())){
            titolareDAO.saveTitolare(titolare);
        }
        InOrder inorder = inOrder(titolareDAO);
        inorder.verify(titolareDAO).checkTitolare(titolare.getEmail());
        inorder.verify(titolareDAO).saveTitolare(titolare);
        //verify(clienteDAO).checkCliente(cliente.getEmail());
        //verify(clienteDAO).saveCliente(cliente);
    }

}
