package com.BarberApp.BackEnd.repository;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class AppuntamentoRepositoryTest {

    @Autowired
    private AppuntamentoRepository repository;

    @Test
    public void saveAppuntamento(){
        Appuntamento appuntamento = new Appuntamento();
    }
}
