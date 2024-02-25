package com.BarberApp.BackEnd.model.titolare;

import java.util.ArrayList;
import java.util.List;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;
import com.BarberApp.BackEnd.model.servizio.Servizio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "titolari")
public class Titolare {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email,
				   password,
				   nome,
				   cognome;
	@OneToMany(mappedBy = "titolare")
	@JsonManagedReference
	private List<Servizio> servizi;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public List<Servizio> getServizi() {
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}

	@Override
	public String toString() {
		return "Titolare{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", servizi=" + servizi +
				'}';
	}
}
