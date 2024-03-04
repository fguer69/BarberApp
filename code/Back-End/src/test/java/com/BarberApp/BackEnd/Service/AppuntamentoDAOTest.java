package com.BarberApp.BackEnd.Service;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import com.BarberApp.BackEnd.repository.AppuntamentoRepositoryTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class AppuntamentoDAOTest {
    @Mock
    private AppuntamentoRepository repository;
    @InjectMocks
    AppuntamentoDAO appuntamentoDAO;

    @Test
    @DisplayName("Test per verificare il salvataggio di un appuntamento")
    void saveAppuntamento(){

    }

    @Test
    @DisplayName("Test per verificare che gli appuntamenti siano mostrati in ordine")
    void getAppuntamentiOrdered() {
        List<Appuntamento> appuntamenti_ordinati = appuntamentoDAO.getAppuntamentiOrdered();
        System.out.println(appuntamenti_ordinati);
    }
}