package model.servizio;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import model.appuntamento.Appuntamento;
import model.titolare.Titolare;

@Entity
@Table(name = "Servizi")
public class Servizio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tipo;
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
	@Override
	public String toString() {
		return "Servizio [id=" + id + ", tipo=" + tipo + ", costo=" + costo + ", appuntamenti=" + appuntamenti
				+ ", titolare=" + titolare + "]";
	}
	
	
}
