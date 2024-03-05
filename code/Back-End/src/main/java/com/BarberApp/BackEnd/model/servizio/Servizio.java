package com.BarberApp.BackEnd.model.servizio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.titolare.Titolare;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "servizi")
public class Servizio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tipo;
	private String assetImage;
	private Double costo;
	@OneToMany(mappedBy = "servizio")
	@JsonManagedReference("servizio")
	private List<Appuntamento> appuntamenti;
	@ManyToOne
	@JoinColumn(name = "titolare_id")
	@JsonBackReference
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

	public List<Appuntamento> getAppuntamenti() {
		return appuntamenti;
	}

	public void setAppuntamenti(List<Appuntamento> appuntamenti) {
		this.appuntamenti = appuntamenti;
	}

	public Titolare getTitolare() {
		return titolare;
	}

	public void setTitolare(Titolare titolare) {
		this.titolare = titolare;
	}

	@Override
	public String toString() {
		ArrayList<Integer> appuntamentiId = new ArrayList<>();
		if(appuntamenti == null){
			appuntamentiId = null;
		}
		else{
			for(int i = 0; i < appuntamenti.size(); i++){
				appuntamentiId.add(appuntamenti.get(i).getId());
			}
		}
		return "Servizio{" +
				"id=" + id +
				", tipo='" + tipo + '\'' +
				", assetImage='" + assetImage + '\'' +
				", costo=" + costo +
				", appuntamenti=" + appuntamentiId +
				", titolare=" + titolare.getId() +
				'}';
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) return false;

		Servizio servizio = (Servizio) obj;
		if (this.id != servizio.getId()) return false;
		if (!this.tipo.equals(servizio.getTipo())) return false;
		if (!this.assetImage.equals(servizio.getAssetImage())) return false;
		if (!Objects.equals(this.costo, servizio.getCosto())) return false;
		if (this.titolare != null && servizio.getTitolare() != null && !this.titolare.equals(servizio.getTitolare())) return false;
		if (this.titolare != null && servizio.getTitolare() == null) return false;
		if (this.titolare == null && servizio.getTitolare() != null) return false;
		if (this.appuntamenti != null && servizio.getAppuntamenti() != null && !this.appuntamenti.equals(servizio.getAppuntamenti())) return false;
		if (this.appuntamenti != null && servizio.getAppuntamenti() == null) return false;
		if (this.appuntamenti == null && servizio.getAppuntamenti() != null) return false;
		if (this.appuntamenti == null && servizio.getAppuntamenti() == null) return true;
		return this.appuntamenti.equals(servizio.getAppuntamenti());
	}
}
