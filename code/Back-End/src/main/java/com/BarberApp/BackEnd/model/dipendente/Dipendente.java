package com.BarberApp.BackEnd.model.dipendente;
import java.util.ArrayList;
import java.util.List;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;


import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;


@Entity
@Table(name = "dipendenti")
public class Dipendente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome,
				   cognome,
				   email,
				   password;
	@OneToMany(mappedBy = "dipendente", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference(value = "dipendente")
	private List<Appuntamento> appuntamenti;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public List<Appuntamento> getAppuntamenti() {
		return appuntamenti;
	}

	public void setAppuntamenti(List<Appuntamento> appuntamenti) {
		this.appuntamenti = appuntamenti;
	}

	@Override
	public String toString() {
		return "Dipendente{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", appuntamenti=" + appuntamenti +
				'}';
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;

		Dipendente dipendente = (Dipendente) obj;
		if (this.id != dipendente.getId()) return false;
		if (!this.nome.equals(dipendente.getNome())) return false;
		if (!this.cognome.equals(dipendente.getCognome())) return false;
		if (!this.email.equals(dipendente.getEmail())) return false;
		if (!this.password.equals(dipendente.getPassword())) return false;
		if (this.appuntamenti != null && dipendente.getAppuntamenti() != null && !this.appuntamenti.equals(dipendente.getAppuntamenti())) return false;
		if (this.appuntamenti == null && dipendente.getAppuntamenti() != null) return false;
		if (this.appuntamenti != null && dipendente.getAppuntamenti() == null) return false;
		if (this.appuntamenti == null && dipendente.getAppuntamenti() == null) return true;
		return this.appuntamenti.equals(dipendente.getAppuntamenti());
	}
}
