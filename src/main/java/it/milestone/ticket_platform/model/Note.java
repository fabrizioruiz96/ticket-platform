package it.milestone.ticket_platform.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @CreationTimestamp
    private LocalDate dateOfCreation;

    @Column(length=1500)
    @Size(min=30, max=1500, message="Il testo deve avere tra i 30 e i 1500 caratteri")
    @NotBlank(message="Il campo non può essere vuoto")
    private String body;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="ticket_id", nullable=false)
    @NotNull
    private Ticket ticket;

    public Note() {
    }

    public Note(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
