package com.BarberApp.BackEnd.model.salone;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "saloni")
public class Salone {
    @Id
    private long  piva;
    private String nomeSalone;
    private String Indirizzo;
    @ManyToMany
    private List<Cliente> clienti;

}
