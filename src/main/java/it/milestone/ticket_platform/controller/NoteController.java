package it.milestone.ticket_platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.milestone.ticket_platform.model.Note;
import it.milestone.ticket_platform.service.NoteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("newNote") Note formNote,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            model.addAttribute("newNote", formNote);
            return "redirect:/ticket/show/" + formNote.getTicket().getId();
        }

        noteService.save(formNote);
        return "redirect:/ticket/show/" + formNote.getTicket().getId();
    }

}
