package org.bootstmytool.notizenspeicherbackend.service;
import org.bootstmytool.backend.model.Note;
import org.bootstmytool.backend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note getNoteById(int id) {
        return noteRepository.findById(id).orElse(null);
    }
}
