package com.BarberApp.BackEnd.controller;

import com.BarberApp.BackEnd.AppuntamentoCustomSerializer;
import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.appuntamento.AppuntamentoDAO;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.hamcrest.CoreMatchers;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppuntamentoController.class)
public class AppuntamentoControllerTest {

    @MockBean
    private AppuntamentoDAO appuntamentoDAO;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;



    Cliente cliente = new Cliente();

    Dipendente dipendente = new Dipendente();

    Servizio servizio = new Servizio();

    Titolare titolare = new Titolare();

    Appuntamento appuntamento = new Appuntamento();

    List<Appuntamento> appuntamenti = new ArrayList<>();
    SimpleModule module = new SimpleModule("AppuntamentoCustomSerializer", new Version(1,0,0,null,null,null));


    @BeforeEach
    public void setUp(){
        cliente.setId(1);
        cliente.setNome("luca");
        cliente.setCognome("lambiase");
        cliente.setEmail("lukesesam94@gmail.com");
        cliente.setPassword("ciaociao");
        dipendente.setId(1);
        dipendente.setNome("Francesco");
        dipendente.setCognome("Dipendente");
        dipendente.setEmail("francescodipendente@gmail.com");
        dipendente.setPassword("ciaociao");
        servizio.setId(1);
        servizio.setTipo("Taglio");
        servizio.setAssetImage("asset/image/taglio.jpg");
        servizio.setCosto(3.0);
        titolare.setId(1);
        titolare.setEmail("titolareuno@gmail.com");
        titolare.setPassword("ciaociao");
        titolare.setNome("titolareuno");
        titolare.setCognome("uno");
        servizio.setTitolare(titolare);
        appuntamento.setId(1);
        appuntamento.setDate(DateTime.now());
        appuntamento.setTime(DateTime.now());
        appuntamento.setCliente(cliente);
        appuntamento.setDipendente(dipendente);
        appuntamento.setServizio(servizio);
        objectMapper.registerModule(new JodaModule());
        module.addSerializer(Appuntamento.class, new AppuntamentoCustomSerializer());
        objectMapper.registerModule(module);
    }

    @Test
    @DisplayName("Mostrare tutti gli appuntamenti disponibili quando si fa una richiesta di tipo GET all'endpoint /appuntamenti/get-all")
    public void listAllAppuntamenti() throws Exception {
        appuntamenti.add(appuntamento);
        when(appuntamentoDAO.getAll()).thenReturn(appuntamenti);
        mockMvc.perform(MockMvcRequestBuilders.get("/appuntamenti/get-all").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(appuntamenti.size()))).andReturn();
        verify(appuntamentoDAO).getAll();

    }
    @Test
    @DisplayName("Salvataggio di un appuntamento nel database")
    public void saveAppuntamento() throws Exception{
        appuntamento.setDate(DateTime.parse("2024-03-07T16:36:47.912Z"));
        appuntamento.setTime(DateTime.parse("2024-03-07T16:36:47.912Z"));
        when(appuntamentoDAO.checkAppuntamento(any(), any())).thenReturn(0);
        doAnswer(invocation -> {
            System.out.println("APPUNTAMENTO SALVATO CON SUCCESSO");
            return null;
        }).when(appuntamentoDAO).saveAppointment(any());
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/appuntamenti/save")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(appuntamento)))
                .andExpect(MockMvcResultMatchers.status().is(200));
        System.out.println(objectMapper.writeValueAsString(appuntamento));
        /*if(appuntamentoDAO.checkAppuntamento(cliente, appuntamento) <= 0){
            appuntamentoDAO.saveAppointment(appuntamento);
        }
        else{
            System.out.println("Salvataggio appuntamento fallita");
        }*/
        //verify(appuntamentoDAO).checkAppuntamento(appuntamento.getCliente(),appuntamento);
        verify(appuntamentoDAO, times(1)).checkAppuntamento(any(), any());
        verify(appuntamentoDAO, times(1)).saveAppointment(any());
    }
}
