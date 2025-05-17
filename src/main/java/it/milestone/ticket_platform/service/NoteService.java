package it.milestone.ticket_platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.milestone.ticket_platform.model.Note;
import it.milestone.ticket_platform.repository.NoteRepository;

@Service
public class NoteService {

    @Autowired 
    private NoteRepository noteRepo;

    public Note getNoteById(Integer id) {
        return noteRepo.findById(id).get();
    }

    public Note save(Note note) {
        return noteRepo.save(note);
    }

    public void deleteById(Integer id) {
        noteRepo.deleteById(id);
    }
}
