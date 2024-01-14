package model.titolare;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import model.appuntamento.Appuntamento;
import model.servizio.Servizio;

@Entity
@Table(name = "Titolari")
public class Titolare {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email,
				   password,
				   nome,
				   cognome;
	@OneToMany(mappedBy = "titolare")
	private List<Appuntamento> appuntamenti;
	@OneToMany(mappedBy = "titolare")
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
	@Override
	public String toString() {
		return "Titolare [id=" + id + ", email=" + email + ", password=" + password + ", nome=" + nome + ", cognome="
				+ cognome + ", appuntamenti=" + appuntamenti + ", servizi=" + servizi + "]";
	}
	
	
	

}
