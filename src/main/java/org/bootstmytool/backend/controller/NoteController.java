package org.bootstmytool.backend.controller;

import org.bootstmytool.backend.model.Image;
import org.bootstmytool.backend.model.Note;
import org.bootstmytool.backend.service.JwtService;
import org.bootstmytool.backend.service.NoteService;
import org.bootstmytool.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.bootstmytool.backend.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the allowed origin as needed
public class NoteController {


    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createNote(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tags") String tags,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {

        logger.info("Received request to create note with title: {}", title);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or invalid.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Authorization header.");
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (username == null) {
            logger.warn("Failed to extract username from token.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token.");
        }

        User user = userService.getUserByUsername(username);
        if (user == null) {
            logger.warn("User not found: {}", username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found.");
        }

        logger.info("Creating note for user: {} (ID: {}, Roles: {})",
                user.getUsername(),
                user.getId());
               // user.getRoles()); // Add more details if needed

        try {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(description);

            List<String> tagList = Arrays.stream(tags.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            note.setTags(tagList);
            note.setUser(user);

            if (images != null && images.length > 0) {
                List<Image> imageList = Arrays.stream(images)
                        .map(image -> {
                            try {
                                Image img = new Image();
                                img.setData(image.getBytes());
                                img.setNote(note);
                                return img;
                            } catch (IOException e) {
                                throw new RuntimeException("Error processing image.", e);
                            }
                        })
                        .collect(Collectors.toList());
                note.setImages(imageList);
            }

            Note savedNote = noteService.createNote(note);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "note", savedNote,
                    "user", Map.of(
                            "username", user.getUsername(),
                            "id", user.getId()
                    )
            ));
        } catch (RuntimeException e) {
            logger.error("Failed to create note for user {}: {}", username, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create note.");
        }
    }

}