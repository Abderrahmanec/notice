
package org.bootstmytool.notizenbackend.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Note> notes;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming every user has a single role "ROLE_USER"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
