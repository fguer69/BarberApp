package com.BarberApp.BackEnd.model.appuntamento;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.BarberApp.BackEnd.model.cliente.Cliente;
import com.BarberApp.BackEnd.model.dipendente.Dipendente;
import com.BarberApp.BackEnd.model.servizio.Servizio;
import com.BarberApp.BackEnd.model.titolare.Titolare;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.joda.time.DateTime;


@Entity
@Table(name = "appuntamenti")
public class Appuntamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JdbcTypeCode(Types.LONGVARBINARY)
	private DateTime date;
	@JdbcTypeCode(Types.LONGVARBINARY)
	private DateTime time;
	//private Boolean flag;
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	@JsonBackReference(value = "cliente")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "dipendente_id")
	@JsonBackReference("dipendente")
	private Dipendente dipendente;
	@ManyToOne
	@JoinColumn(name = "servizio_id")
	@JsonBackReference(value = "servizio")
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
	public Dipendente getDipendente() {
		return dipendente;
	}
	public void setDipendente(Dipendente dipendente) {
		this.dipendente = dipendente;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		String clienteId = cliente != null ? String.valueOf(cliente.getId()) : "null";
		String dipendenteId = dipendente != null ? String.valueOf(dipendente.getId()) : "null";
		String servizioId = servizio != null ? String.valueOf(servizio.getId()) : "null";
		return "Appuntamento{" +
				"id=" + id +
				", date=" + date +
				", time=" + time +
				", clienteId=" + clienteId  +
				", dipendente=" + dipendenteId  +
				", servizio=" + servizioId +
				'}';
	}
}
