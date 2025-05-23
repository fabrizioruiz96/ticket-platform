package it.milestone.ticket_platform.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.milestone.ticket_platform.model.Note;
import it.milestone.ticket_platform.model.Ticket;
import it.milestone.ticket_platform.model.TicketState;
import it.milestone.ticket_platform.repository.NoteRepository;
import it.milestone.ticket_platform.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private NoteRepository noteRepo;

    public List<Ticket> findTicket(String title, Integer cat, TicketState state) {

        List<Ticket> result;
        boolean hasTitle = title != null && !title.isBlank();
        boolean hasCat = cat != null;
        boolean hasState = state != null;

        if (hasTitle && hasCat && hasState) {
            result = ticketRepo.findByTitleContainingIgnoreCaseAndCategoryIdAndState(title, cat, state);
        } else if (hasTitle && hasCat) {
            result = ticketRepo.findByTitleContainingIgnoreCaseAndCategoryId(title, cat);
        } else if (hasTitle && hasState) {
            result = ticketRepo.findByTitleContainingIgnoreCaseAndState(title, state);
        } else if (hasCat && hasState) {
            result = ticketRepo.findByCategoryIdAndState(cat, state);
        } else if (hasTitle) {
            result = ticketRepo.findByTitleContainingIgnoreCase(title);
        } else if (hasCat) {
            result = ticketRepo.findByCategoryId(cat);
        } else if (hasState) {
            result = ticketRepo.findByState(state);
        } else {
            result = ticketRepo.findAll();
        }
        return result;
    }

    public List<Ticket> findAll() {
        return ticketRepo.findAll();
    }

    public List<Ticket> findByCategory(Integer id) {
        return ticketRepo.findByCategoryId(id);
    }

    public List<Ticket> findByState(TicketState state) {
        return ticketRepo.findByState(state);
    }

    public List<Ticket> findByUser(Integer id) {
        return ticketRepo.findByUser_Id(id);
    }

    public Optional<Ticket> findById(Integer id) {
        return ticketRepo.findById(id);
    }

    public List<Ticket> findByUserAndState(Integer id, TicketState state) {
        return ticketRepo.findByUser_IdAndState(id, state);
    }

    public Ticket save(Ticket ticket) {
        return ticketRepo.save(ticket);
    }

    public void delete(Integer id) {
        Ticket ticket = ticketRepo.findById(id).get();
        for (Note note : ticket.getNotes()) {
            noteRepo.deleteById(note.getId());
        }
        ticketRepo.deleteById(id);
    }
}
