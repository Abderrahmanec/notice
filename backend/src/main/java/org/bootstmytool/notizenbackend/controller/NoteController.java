package org.bootstmytool.notizenbackend.controller;

import org.bootstmytool.notizenbackend.model.Note;
import org.bootstmytool.notizenbackend.model.Image;
import org.bootstmytool.notizenbackend.model.User;
import org.bootstmytool.notizenbackend.services.AuthService;
import org.bootstmytool.notizenbackend.services.JwtService;
import org.bootstmytool.notizenbackend.services.NoteService;
import org.bootstmytool.notizenbackend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:3000") // Adjust the allowed origin as needed
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService, JwtService jwtService) {
        this.noteService = noteService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    private static final String IMAGE_DIR = "src/main/resources/static/images/"; // Directory where images are stored

    // Process image and store it on the file system
    private Image processImage(MultipartFile file) {
        try {
            // Create directory if it doesn't exist
            Path imagePath = Path.of("src/main/resources/static/images/"); // Correct directory for static images
            if (!Files.exists(imagePath)) {
                Files.createDirectories(imagePath);
            }

            // Create a unique filename by appending a timestamp
            String imageName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path targetPath = imagePath.resolve(imageName);

            // Save image to the file system
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Create image entity
            Image image = new Image();
            image.setData(file.getBytes());  // Store image data as binary (if needed)
            image.setUrl(imageName);  // Store only the image name
            return image;
        } catch (IOException e) {
            logger.warn("Error processing image {}: {}", file.getOriginalFilename(), e.getMessage());
            throw new RuntimeException("Error processing image.");
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createNotes(@RequestHeader("Authorization") String authHeader,
                                         @RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("tags") String tags,
                                         @RequestParam(value = "images", required = false) MultipartFile[] images) {
        try {
            User user = validateAuthorization(authHeader);
            Note note = buildNoteObject(title, description, tags, images, user);
            Note savedNote = noteService.createNote(note);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
        } catch (IllegalArgumentException | SecurityException e) {
            logger.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating note: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create note.");
        }
    }

    private User validateAuthorization(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new SecurityException("Invalid Authorization header.");
        }
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        if (username == null) {
            throw new SecurityException("Invalid token.");
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new SecurityException("User not found.");
        }
        return user;
    }

    private Note buildNoteObject(String title, String description, String tags, MultipartFile[] images, User user) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(description);
        note.setTags(Arrays.stream(tags.split(",")).map(String::trim).collect(Collectors.toList()));
        note.setUser(user);

        if (images != null) {
            // Add processed images to the note
            note.setImages(Arrays.stream(images).map(this::processImage).collect(Collectors.toList()));
        }

        return note;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Note>> getNotesForUser(@RequestHeader("Authorization") String authHeader) {
        logger.info("Received request to get notes for user");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or invalid.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }

        String token = authHeader.substring(7);

        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            logger.error("Error extracting username from token", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }

        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            logger.warn("User not found: {}", username);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }

        User user = optionalUser.get();
        logger.info("Fetching notes for user: {} (ID: {})", user.getUsername(), user.getId());

        try {
            List<Note> notes = noteService.getNotesByUserId((int) user.getId());

            if (notes.isEmpty()) {
                logger.info("No notes found for user ID: {}", user.getId());
                return ResponseEntity.noContent().build();
            }

            // Ensure the image URL is set correctly for each note
            notes.forEach(note -> {
                if (note.getImages() != null) {
                    note.getImages().forEach(image -> {
                        String imageName = image.getUrl();
                        image.setUrl("http://localhost:8080/images/" + imageName);
                        logger.info("Constructed image URL: {}", image.getUrl());
                    });
                }
            });

            return ResponseEntity.ok(notes);
        } catch (Exception e) {
            logger.error("Error retrieving notes for user ID: {}", user.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

/*
    @PostMapping("/{noteId}/images")
    public void addImageToNote(@PathVariable int noteId, @RequestBody Image image) {
        noteService.addImageToNote(noteId, image);
    }



 */




}