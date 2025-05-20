package it.milestone.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.milestone.ticket_platform.model.UserState;
import it.milestone.ticket_platform.security.DatabaseUserDetailsService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired 
    private DatabaseUserDetailsService userService;

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("stateList", UserState.values());

        return "/user/show";
    }
    
}
