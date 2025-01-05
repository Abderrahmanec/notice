package org.bootstmytool.notizenbackend.repository;

import org.bootstmytool.notizenbackend.model.Note;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    // Method to find notes by user ID with pagination
    List<Note> findByUserId(int userId, Pageable pageable);

    // Method to count notes by user ID
    int countByUserId(int userId);

    List<Note> findByUserId(int userId);



    List<Note> findAllByUserId(int userId); // Find all notes for a user

        Optional<Note> findById(int noteId); // Find a note by its ID

    //    public List<Note> findByUserId(int userId);


        void deleteById(int noteId); // Delete a note by its ID

        void deleteAllByUserId(int userId); // Delete all notes for a user

        void deleteAllByUserIdAndId(int userId, int noteId); // Delete a note by its ID and user ID

        void deleteAllByUserIdAndIdIn(int userId, List<Integer> noteIds); // Delete multiple notes by their IDs and user ID



    }
