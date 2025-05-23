package it.milestone.ticket_platform.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import it.milestone.ticket_platform.model.Note;
import it.milestone.ticket_platform.model.Ticket;
import it.milestone.ticket_platform.model.TicketState;
import it.milestone.ticket_platform.model.User;
import it.milestone.ticket_platform.model.UserState;
import it.milestone.ticket_platform.repository.CategoryRepository;
import it.milestone.ticket_platform.security.DatabaseUserDetails;
import it.milestone.ticket_platform.security.DatabaseUserDetailsService;
import it.milestone.ticket_platform.service.TicketService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private DatabaseUserDetailsService userService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("catList", catRepo.findAll());
        model.addAttribute("editMode", false);
        model.addAttribute("userList", userService.findByState(UserState.ATTIVO));
        return "/ticket/edit";
    }

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("ticket") Ticket formTicket,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            model.addAttribute("catList", catRepo.findAll());
            model.addAttribute("userList", userService.findByState(UserState.ATTIVO));
            return "/ticket/edit";
        }

        ticketService.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket creato");

        return "redirect:/ticket";
    }

    @GetMapping
    public String index(@AuthenticationPrincipal DatabaseUserDetails user,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "category", required = false) Integer cat,
            @RequestParam(name = "state", required = false) TicketState state,
            Model model) {

        if (userService.isOperator(user)) {
            model.addAttribute("ticketList", ticketService.findByUser(user.getId()));
        } else {
            model.addAttribute("ticketList", ticketService.findTicket(title, cat, state));
            model.addAttribute("isAdmin", true);
        }

        if (userService.findByState(UserState.ATTIVO).isEmpty()) {
            model.addAttribute("userAvailable", false);
        } else {
            model.addAttribute("userAvailable", true);
        }

        model.addAttribute("catList", catRepo.findAll());
        model.addAttribute("stateList", TicketState.values());
        model.addAttribute("user", userService.findById(user.getId()));

        return "ticket/index";
    }

    @GetMapping("/show/{id}")
    public String show(@AuthenticationPrincipal DatabaseUserDetails user,
            @PathVariable("id") Integer id, Model model) {

        Optional<Ticket> optTicket = ticketService.findById(id);

        if (!optTicket.isPresent()) {
            model.addAttribute("errorMessage", "Errore di ricerca del ticket");
            model.addAttribute("errorCause", "L'id inserito non è valido");
            return "/error_pages/generalError";
        }

        User operator = ticketService.findById(id).get().getUser();
        UserState state = operator.getState();
        if(state == UserState.NON_ATTIVO) {
            model.addAttribute("editState", false);
        } else {
            model.addAttribute("editState", true);
        }

        model.addAttribute("newNote", new Note(ticketService.findById(id).get(), userService.findById(user.getId())));
        model.addAttribute("ticket", optTicket.get());
        model.addAttribute("stateList", TicketState.values());
        model.addAttribute("noteList", optTicket.get().getNotes());

        return "/ticket/show";
    }

    @PostMapping("/updateState")
    public String updateState(@RequestParam("id") Integer id, TicketState state) {
        Ticket ticket = ticketService.findById(id).get();
        ticket.setState(state);
        ticketService.save(ticket);
        return "redirect:/ticket/show/" + id;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("ticket", ticketService.findById(id).get());
        model.addAttribute("catList", catRepo.findAll());
        model.addAttribute("editMode", true);
        model.addAttribute("userList", userService.findByState(UserState.ATTIVO));

        return "/ticket/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(
            @Valid @ModelAttribute("ticket") Ticket formTicket,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", formTicket);
            model.addAttribute("catList", catRepo.findAll());
            model.addAttribute("editMode", true);
            model.addAttribute("userList", userService.findByState(UserState.ATTIVO));
            return "/ticket/edit";
        }

        Ticket existing = ticketService.findById(formTicket.getId()).get();
        formTicket.setDateOfCreation(existing.getDateOfCreation());

        ticketService.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket modificato!");

        return "redirect:/ticket";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ticketService.delete(id);
        return "redirect:/ticket";
    }

    @GetMapping("/{id}/note")
    public String note(@AuthenticationPrincipal DatabaseUserDetails userDetails,
            @PathVariable("id") Integer id, Model model) {
        Ticket ticket = ticketService.findById(id).get();
        User user = userService.findById(userDetails.getId());
        Note note = new Note(ticket, user);

        model.addAttribute("note", note);
        model.addAttribute("editMode", false);

        return "/note/edit";
    }
}
