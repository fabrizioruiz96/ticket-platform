package it.milestone.ticket_platform.controller;

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

import it.milestone.ticket_platform.model.TicketState;
import it.milestone.ticket_platform.model.User;
import it.milestone.ticket_platform.model.UserState;
import it.milestone.ticket_platform.security.DatabaseUserDetailsService;
import it.milestone.ticket_platform.service.TicketService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private DatabaseUserDetailsService userService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        if (ticketService.findByUserAndState(id, TicketState.DA_FARE).isEmpty()
                && ticketService.findByUserAndState(id, TicketState.IN_CORSO).isEmpty()) {
            model.addAttribute("editState", true);
        } else {
            model.addAttribute("editState", false);
        }

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("stateList", UserState.values());

        return "/user/show";
    }

    @PostMapping("/updateState")
    public String updateState(@RequestParam("id") Integer id, UserState state) {
        User user = userService.findById(id);
        user.setState(state);
        userService.save(user);
        return "redirect:/ticket";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @Valid @ModelAttribute("user") User formUser,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", formUser);
            return "/user/edit";
        }

        userService.save(formUser);

        return "redirect:/user/show/" + formUser.getId();
    }
}
