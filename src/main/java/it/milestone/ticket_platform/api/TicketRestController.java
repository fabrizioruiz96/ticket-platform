package it.milestone.ticket_platform.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.milestone.ticket_platform.model.Ticket;
import it.milestone.ticket_platform.model.TicketState;
import it.milestone.ticket_platform.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @GetMapping()
    public ResponseEntity<List<Ticket>> index() {
        List<Ticket> ticket = ticketService.findAll();
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(ticket, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Ticket>> filterByCategory(@PathVariable("id") Integer id) {
        List<Ticket> ticket = ticketService.findByCategory(id);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(ticket, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Ticket>> filterByState(@PathVariable("state") TicketState state) {
        List<Ticket> ticket = ticketService.findByState(state);
        if (ticket.isEmpty()) {
            return new ResponseEntity<>(ticket, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(ticket, HttpStatus.OK);
        }
    }

}
