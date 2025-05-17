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

import it.milestone.ticket_platform.model.Note;
import it.milestone.ticket_platform.service.NoteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("note") Note formNote,
            BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            model.addAttribute("note", formNote);
            return "/note/edit";
        }

        noteService.save(formNote);
        return "redirect:/ticket/show/" + formNote.getTicket().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("note", noteService.getNoteById(id));
        model.addAttribute("editMode", true);
        return "/note/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("note") Note formNote,
            BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()) {
            model.addAttribute("note", formNote);
            model.addAttribute("editMode", true);
            return "/note/edit";
        }

        noteService.save(formNote);
        return "redirect:/ticket/show/" + formNote.getTicket().getId();
    }
}
