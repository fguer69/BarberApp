package com.BarberApp.BackEnd.model.servizio;

import java.util.ArrayList;
import java.util.List;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.titolare.Titolare;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Servizi")
public class Servizio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tipo;
	private String assetImage;
	private Double costo;
	@OneToMany(mappedBy = "servizio")
	private List<Appuntamento> appuntamenti;
	@ManyToOne
	@JoinColumn(name = "titolare_id")
	private Titolare titolare;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	public String getAssetImage() {
		return assetImage;
	}
	public void setAssetImage(String assetImage) {
		this.assetImage = assetImage;
	}
	@Override
	public String toString() {
		return "Servizio [id=" + id + ", tipo=" + tipo + ", costo=" + costo + ", appuntamenti=" + appuntamenti
				+ ", titolare=" + titolare + "]";
	}
	
	
}
