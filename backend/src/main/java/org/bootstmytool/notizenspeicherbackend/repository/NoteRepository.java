package org.bootstmytool.notizenspeicherbackend.repository;


import org.bootstmytool.notizenspeicherbackend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserId(Long userId);



}
