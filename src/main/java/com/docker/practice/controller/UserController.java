package com.docker.practice.controller;

import com.docker.practice.domain.User;
import com.docker.practice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PutMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.NOT_FOUND)
            );
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable long id) {
        try {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new HttpClientErrorException(HttpStatus.NO_CONTENT)
            );
            userRepository.deleteById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>(ex.getStatusCode());
        }
    }
}
