package com.BarberApp.BackEnd.model.OrariServizio;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.joda.time.LocalTime;
import com.BarberApp.BackEnd.model.salone.Salone;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orari_servizio")
public class OrariServizio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int weekDay;
    private LocalTime Start_hour;
    private LocalTime End_hour;
    @ManyToOne
    @JoinColumn(name = "salone_id")
    @JsonManagedReference
    private Salone salone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(int weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getStart_hour() {
        return Start_hour;
    }

    public void setStart_hour(LocalTime start_hour) {
        Start_hour = start_hour;
    }

    public LocalTime getEnd_hour() {
        return End_hour;
    }

    public void setEnd_hour(LocalTime end_hour) {
        End_hour = end_hour;
    }

    public Salone getSalone() {
        return salone;
    }

    public void setSalone(Salone salone) {
        this.salone = salone;
    }

    @Override
    public String toString() {
        return "OrariServizio{" +
                "id=" + id +
                ", weekDay=" + weekDay +
                ", Start_hour=" + Start_hour +
                ", End_hour=" + End_hour +
                ", salone=" + salone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrariServizio that = (OrariServizio) o;
        return id == that.id && weekDay == that.weekDay && Objects.equals(Start_hour, that.Start_hour) && Objects.equals(End_hour, that.End_hour) && Objects.equals(salone, that.salone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weekDay, Start_hour, End_hour, salone);
    }
}
