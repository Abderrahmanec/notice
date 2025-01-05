package org.bootstmytool.notizenbackend.controller;

import org.bootstmytool.notizenbackend.model.Image;
import org.bootstmytool.notizenbackend.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    // Endpoint to get images by noteId
    @GetMapping("/note/{noteId}")
    public List<Image> getImagesByNoteId(@PathVariable int noteId) {
        return imageService.getImagesByNoteId(noteId);
    }


}