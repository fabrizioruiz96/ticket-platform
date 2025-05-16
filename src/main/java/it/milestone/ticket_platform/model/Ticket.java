package it.milestone.ticket_platform.model;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length=150)
    @Size(min=10, max=150, message="Il testo deve avere tra i 10 e i 150 caratteri")
    @NotBlank(message="Il campo non può essere vuoto")
    private String title;

    @CreationTimestamp
    private LocalDate dateOfCreation;

    @Column(length=3000)
    @Size(min=30, max=3000, message="Il testo deve avere tra i 30 e i 3000 caratteri")
    @NotBlank(message="Il campo non può essere vuoto")
    private String body;

    @Enumerated(EnumType.STRING)
    private TicketState state = TicketState.DA_FARE;

    @ManyToOne
    @JoinColumn(name="category_id", nullable=false)
    @JsonBackReference
    @NotNull(message="Seleziona una categoria")
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}
