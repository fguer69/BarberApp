package com.BarberApp.BackEnd.model.salone;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "saloni")
public class Salone {
    @Id
    private long  piva;
    private String nomeSalone;
    private String Indirizzo;
    @ManyToMany(mappedBy = "saloni")
    @JsonBackReference
    private List<Cliente> clienti;
    @OneToMany(mappedBy =  "salone", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonBackReference
    private List<Dipendente> dipendenti;
    @OneToMany(mappedBy = "salone", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonBackReference
    private List<Appuntamento> appuntamenti;
    @OneToMany(mappedBy = "salone", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonBackReference
    private List<Servizio> servizi;
    @OneToMany(mappedBy =  "salone", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonBackReference
    private List<Titolare> titolari;

    public List<Appuntamento> getAppuntamenti() {
        return appuntamenti;
    }

    public void setAppuntamenti(List<Appuntamento> appuntamenti) {
        this.appuntamenti = appuntamenti;
    }

    public List<Cliente> getClienti() {
        return clienti;
    }

    public void setClienti(List<Cliente> clienti) {
        this.clienti = clienti;
    }

    public List<Dipendente> getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(List<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }

    public String getIndirizzo() {
        return Indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        Indirizzo = indirizzo;
    }

    public String getNomeSalone() {
        return nomeSalone;
    }

    public void setNomeSalone(String nomeSalone) {
        this.nomeSalone = nomeSalone;
    }

    public long getPiva() {
        return piva;
    }

    public void setPiva(long piva) {
        this.piva = piva;
    }

    public List<Servizio> getServizi() {
        return servizi;
    }

    public void setServizi(List<Servizio> servizi) {
        this.servizi = servizi;
    }

    public List<Titolare> getTitolari() {
        return titolari;
    }

    public void setTitolari(List<Titolare> titolari) {
        this.titolari = titolari;
    }

    @Override
    public String toString() {
        return "Salone{" +
                "appuntamenti=" + appuntamenti +
                ", piva=" + piva +
                ", nomeSalone='" + nomeSalone + '\'' +
                ", Indirizzo='" + Indirizzo + '\'' +
                ", clienti=" + clienti +
                ", dipendenti=" + dipendenti +
                ", servizi=" + servizi +
                ", titolari=" + titolari +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salone salone = (Salone) o;
        return getPiva() == salone.getPiva() && Objects.equals(getNomeSalone(), salone.getNomeSalone()) && Objects.equals(getIndirizzo(), salone.getIndirizzo()) && Objects.equals(getClienti(), salone.getClienti()) && Objects.equals(getDipendenti(), salone.getDipendenti()) && Objects.equals(getAppuntamenti(), salone.getAppuntamenti()) && Objects.equals(getServizi(), salone.getServizi()) && Objects.equals(getTitolari(), salone.getTitolari());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPiva(), getNomeSalone(), getIndirizzo(), getClienti(), getDipendenti(), getAppuntamenti(), getServizi(), getTitolari());
    }
}
