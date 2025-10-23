package com.example.GenesisResourcesBackend.controller;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> saveUser(@RequestBody PostUserDTO postUserDTO) {
        try {
            this.userService.saveUser(postUserDTO);
            return new ResponseEntity<>(postUserDTO, HttpStatus.CREATED);
        } catch (ValidationException | UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
