package it.milestone.ticket_platform.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(length=150)
    @Size(min=10, max=150, message="Max 150 caratteri")
    @NotBlank(message="Il campo non può essere vuoto")
    private String title;

    @Column(updatable=false)
    private LocalDateTime dateOfCreation;

    @Column(length=3000)
    @Size(min=30, max=3000, message="Max 3000 caratteri")
    @NotBlank(message="Il campo non può essere vuoto")
    private String body;

    @Enumerated(EnumType.STRING)
    private TicketState state;

    @PrePersist
    private void onCreation() {
        if (dateOfCreation == null) {
            dateOfCreation = LocalDateTime.now();
        }
        if (state == null) {
            state = state.DA_FARE;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
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
