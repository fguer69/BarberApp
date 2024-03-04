package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = AppuntamentoController.class)
public class AppuntamentoControllerTest {

    @MockBean
    private AppuntamentoDAO appuntamentoDAO;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Mostrare tutti gli appuntamenti disponibili quando si fa una richiesta di tipo GET all'endpoint /appuntamenti/get-all")
    public void listAllAppuntamenti() throws Exception {
        List<Appuntamento> appuntamenti = new ArrayList<>();
        mockMvc.perform(MockMvcRequestBuilders.get("/appuntamenti/get-all").contentType(MediaType.APPLICATION_JSON)).andDo(print());
    }
}
