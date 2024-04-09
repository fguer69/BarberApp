package com.BarberApp.BackEnd.model.salone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "saloni")
public class Salone {
    @Id
    private long  piva;
}
