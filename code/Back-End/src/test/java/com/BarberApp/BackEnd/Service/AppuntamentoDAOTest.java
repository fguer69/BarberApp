package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class AppuntamentoDAOTest {
    @Mock
    private AppuntamentoRepository repository;
    AppuntamentoDAO appuntamentoDAO;
    @BeforeEach
    public void setup(){
        appuntamentoDAO = new AppuntamentoDAO();
    }

    @Test
    @DisplayName("Test per verificare che gli appuntamenti siano mostrati in ordine")
    void getAppuntamentiOrdered() {
        List<Appuntamento> appuntamenti_ordinati = appuntamentoDAO.getAppuntamentiOrdered();
    }
}