package org.bootstmytool.notizenbackend.services;

import org.bootstmytool.notizenbackend.model.Image;
import org.bootstmytool.notizenbackend.model.Note;
import org.bootstmytool.notizenbackend.repository.ImageRepository;
import org.bootstmytool.notizenbackend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, ImageRepository imageRepository) {
        this.noteRepository = noteRepository;
        this.imageRepository = imageRepository;
    }

    public Note createNote(Note note) {
        // Save the note first
        Note savedNote = noteRepository.save(note);

        // Ensure images are associated with the saved note
        if (note.getImages() != null) {
            for (Image image : note.getImages()) {
                image.setNote(savedNote);  // Associate image with the saved note
                imageRepository.save(image);  // Save the image with the note_id
            }
        }

        return savedNote;
    }

    public List<Note> getNotesByUserId(int id) {
        return noteRepository.findByUserId(id);
    }

    public void addImageToNote(int noteId, Image image) {
        // Fetch the note by ID
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));

        // Associate the note with the image
        image.setNote(note);

        // Save the image (cascade will save it with the note automatically)
        imageRepository.save(image);
    }
}
