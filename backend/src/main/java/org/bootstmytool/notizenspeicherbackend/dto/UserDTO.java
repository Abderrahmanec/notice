package org.bootstmytool.notizenspeicherbackend.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
