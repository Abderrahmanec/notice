package org.bootstmytool.notizenspeicherbackend.repository;




import org.bootstmytool.backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> { // ID is Integer, not Long
    List<Note> findAllByUserId(int userId); // Find all notes for a user

    Optional<Note> findById(int noteId); // Find a note by its ID
}
