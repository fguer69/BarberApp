package com.BarberApp.BackEnd.model.cliente;
import java.util.ArrayList;
import java.util.List;

import com.BarberApp.BackEnd.model.appuntamento.Appuntamento;

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
@Table(name = "Clienti")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nome,
				   cognome,
				   email,
				   password;
	@OneToMany(mappedBy = "cliente")
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
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", password="
				+ password + ", appuntamenti=" + appuntamenti + "]";
	}
	
	
	
	
				   

}
