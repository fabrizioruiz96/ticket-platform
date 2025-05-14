package it.milestone.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.milestone.ticket_platform.service.TicketService;


@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public String index(@RequestParam(name="keyword", required=false) String title, Model model) {
        
        model.addAttribute("list", ticketService.findTicket(title));

        return "ticket/index";
    }
    
}
