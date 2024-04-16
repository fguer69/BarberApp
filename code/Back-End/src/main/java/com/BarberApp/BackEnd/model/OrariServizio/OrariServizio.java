package com.BarberApp.BackEnd.model.OrariServizio;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orari_servizio")
public class OrariServizio {
    @Id
    private int id;
}
