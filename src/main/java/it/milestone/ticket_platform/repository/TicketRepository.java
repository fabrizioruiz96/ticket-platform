package it.milestone.ticket_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.milestone.ticket_platform.model.Ticket;
import it.milestone.ticket_platform.model.TicketState;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    public List<Ticket> findByTitleContainingIgnoreCase(String title);
    public List<Ticket> findByCategoryId(Integer cat);
    public List<Ticket> findByState(TicketState state);
    public List<Ticket> findByTitleContainingIgnoreCaseAndCategoryId(String title, Integer cat);
    public List<Ticket> findByTitleContainingIgnoreCaseAndState(String title, TicketState state);
    public List<Ticket> findByCategoryIdAndState(Integer cat, TicketState state);
    public List<Ticket> findByTitleContainingIgnoreCaseAndCategoryIdAndState(String title, Integer cat, TicketState state);

    public List<Ticket> findByUser_Id(Integer user);
    public List<Ticket> findByUser_IdAndState(Integer user, TicketState state);
}
