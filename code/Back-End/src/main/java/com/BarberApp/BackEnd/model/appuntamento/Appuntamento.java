package com.BarberApp.BackEnd.model.appuntamento;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.*;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;

import jakarta.persistence.*;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "appuntamenti")
public class Appuntamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private DateTime date;
	private DateTime time;
	//private Boolean flag;
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "dipendente_id")
	private Dipendente dipendente;
	@ManyToOne
	@JoinColumn(name = "titolare_id")
	private Titolare titolare;
	@ManyToOne
	@JoinColumn(name = "servizio_id")
	private Servizio servizio;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
	public DateTime getTime() {
		return time;
	}
	public void setTime(DateTime time) {
		this.time = time;
	}
	public Dipendente getEmployee() {
		return dipendente;
	}
	public void setEmployee(Dipendente dipendente) {
		this.dipendente = dipendente;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	public Titolare getTitolare() {
		return titolare;
	}
	public void setTitolare(Titolare titolare) {
		this.titolare = titolare;
	}
	public Servizio getServizio() {
		return servizio;
	}
	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}
	/*public Boolean getFlag() {
		return flag;
	}*/
	/*public void setFlag(Boolean flag) {
		this.flag = flag;
	}*/

	@Override
	public String toString() {
		return "Appuntamento{" +
				"id=" + id +
				", date=" + date +
				", time=" + time +
				", cliente=" + cliente +
				", dipendente=" + dipendente +
				", titolare=" + titolare +
				", servizio=" + servizio +
				'}';
	}
}
