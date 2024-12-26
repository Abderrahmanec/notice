package org.bootstmytool.notizenspeicherbackend.controller;

import org.bootstmytool.notizenspeicherbackend.dto.NoteDTO;
import org.bootstmytool.notizenspeicherbackend.model.Note;
import org.bootstmytool.notizenspeicherbackend.model.User;
import org.bootstmytool.notizenspeicherbackend.services.NoteService;
import org.bootstmytool.notizenspeicherbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @PostMapping
    public Note createNote(@RequestBody NoteDTO noteDTO, @RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return noteService.createNote(noteDTO, user);
    }

    @GetMapping
    public List<Note> getNotes(@RequestParam String username) {
        User user = userService.getUserByUsername(username);
        return noteService.getNotesByUser(user);
    }

    @GetMapping("/hi")
    public String hi() {
        System.out.println("hi");
        return "Hi";
    }

    @GetMapping("/ji")
    public String jil() {
        System.out.println("hdftgzhjki");
        return "index.html";
    }




}
