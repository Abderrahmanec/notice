package org.bootstmytool.notizenbackend.repository;

import org.bootstmytool.notizenbackend.model.Image;
import org.bootstmytool.notizenbackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> checkIfUsernameExists(@Param("username") String username);

    interface ImageRepository extends JpaRepository<Image, Integer> {

        // Find all images for a note by noteId
        List<Image> findByNoteId(int noteId);

        // Retrieve paginated images for a specific note
        Page<Image> findByNoteId(int noteId, Pageable pageable);

        // Find images for a note created after a certain date
        List<Image> findByNoteIdAndCreatedDateAfter(int noteId, LocalDateTime createdDate);

        // Find an image by its ID
        Optional<Image> findById(int imageId);

        // Retrieve all images for a specific note
        List<Image> findAllByNoteId(int noteId);
    }
}

