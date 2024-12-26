package org.bootstmytool.notizenspeicherbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bootstmytool.notizenspeicherbackend.dto.UserDTO;
import org.bootstmytool.notizenspeicherbackend.model.User;
import org.bootstmytool.notizenspeicherbackend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
      //  user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode password
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
