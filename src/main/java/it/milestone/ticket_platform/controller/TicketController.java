package it.milestone.ticket_platform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.milestone.ticket_platform.model.Ticket;
import it.milestone.ticket_platform.service.TicketService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("editMode", false);
        return "/ticket/edit";
    }

    @PostMapping("/create")
    public String store(
        @Valid @ModelAttribute("ticket") Ticket formTicket,
        BindingResult bindingResult,
        Model model,
        RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            return "/ticket/edit";
        }

        ticketService.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket creato");

        return "redirect:/ticket";
    }

    @GetMapping
    public String index(@RequestParam(name="keyword", required=false) String title, Model model) {
        
        model.addAttribute("list", ticketService.findTicket(title));

        return "ticket/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Optional<Ticket> optTicket = ticketService.findById(id);  
        
        if(!optTicket.isPresent()) {
            model.addAttribute("errorMessage", "Errore di ricerca del ticket");
            model.addAttribute("errorCause", "L'id inserito non è valido");
            return "/error_pages/generalError";
        }

        model.addAttribute("ticket", optTicket.get());

        return "/ticket/show";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketService.findById(id).get());
        model.addAttribute("editMode", true);
        return "/ticket/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
        @Valid @ModelAttribute("ticket") Ticket formTicket,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes,
        Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("ticket", formTicket);
            model.addAttribute("editMode", true);
            return "/ticket/edit";
        }

        ticketService.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket modificato!");

        return "redirect:/ticket";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ticketService.delete(id);
        return "redirect:/ticket";
    }    
}
