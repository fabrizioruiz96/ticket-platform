package it.milestone.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.milestone.ticket_platform.model.Ticket;
import it.milestone.ticket_platform.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepo;

    public List<Ticket> findTicket (String title) {
        List<Ticket> result;

        if (title != null && !title.isBlank()) {
            result = ticketRepo.findByTitleContainingIgnoreCase(title);
        } else {
            result = ticketRepo.findAll();
        }

        return result;
    }

    public Optional<Ticket> findById (Integer id) {
        return ticketRepo.findById(id);
    }

    public Ticket save (Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public void delete (Integer id) {
        ticketRepo.deleteById(id);
    }
}
