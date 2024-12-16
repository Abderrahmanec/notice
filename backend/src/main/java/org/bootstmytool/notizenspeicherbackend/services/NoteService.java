package org.bootstmytool.notizenspeicherbackend.services;

import org.bootstmytool.notizenspeicherbackend.dto.NoteDTO;
import org.bootstmytool.notizenspeicherbackend.model.Note;
import org.bootstmytool.notizenspeicherbackend.model.User;
import org.bootstmytool.notizenspeicherbackend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note createNote(NoteDTO noteDTO, User user) {
        Note note = new Note();
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setTags(noteDTO.getTags());
        note.setUser(user);
        return noteRepository.save(note);
    }

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findAllByUserId(user.getId());
    }
}
