package org.bootstmytool.notizenbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String url;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "note_id")
    @JsonBackReference
    private Note note;

    @Column(name = "created_date")
    private LocalDateTime createdDate; // New field




    // Constructors, getters, and setters
    public Image() {
        this.createdDate=LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public String getExtension() {
        return url.substring(url.lastIndexOf(".") + 1);
    }
}
