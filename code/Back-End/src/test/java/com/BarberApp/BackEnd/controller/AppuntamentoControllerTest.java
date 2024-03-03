package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = AppuntamentoController.class)
public class AppuntamentoControllerTest {

    @MockBean
    private AppuntamentoDAO appuntamentoDAO;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Mostrare tutti gli appuntamenti disponibili quando si fa una richiesta di tipo GET all'endpoint /appuntamenti/get-all")
    public void listAllAppuntamenti() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/appuntamenti/get-all"));
    }
}
