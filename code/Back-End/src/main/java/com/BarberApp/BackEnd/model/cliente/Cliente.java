package com.BarberApp.BackEnd.model.cliente;
import java.util.ArrayList;
import java.util.List;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clienti")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome,
				   cognome,
				   email,
				   password;
	@OneToMany(mappedBy = "cliente")
	@JsonManagedReference(value = "cliente")
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
		return "Cliente{" +
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

		Cliente cliente = (Cliente) obj;
		if (this.id != cliente.getId()) return false;
		if (!this.nome.equals(cliente.getNome())) return false;
		if (!this.cognome.equals(cliente.getCognome())) return false;
		if (!this.email.equals(cliente.getEmail())) return false;
		if (!this.password.equals(cliente.getPassword())) return false;
		if (this.appuntamenti != null && cliente.getAppuntamenti() != null && !this.appuntamenti.equals(cliente.getAppuntamenti())) return false;
		if (this.appuntamenti == null && cliente.getAppuntamenti() != null) return false;
		if (this.appuntamenti != null && cliente.getAppuntamenti() == null) return false;
		if (this.appuntamenti == null && cliente.getAppuntamenti() == null) return true;
        return this.appuntamenti.equals(cliente.getAppuntamenti());
    }
}
