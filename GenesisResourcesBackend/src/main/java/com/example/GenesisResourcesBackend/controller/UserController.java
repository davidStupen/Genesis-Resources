package com.example.GenesisResourcesBackend.controller;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserNotDetailsDTO;
import com.example.GenesisResourcesBackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody PostUserDTO postUserDTO) {
        try {
            this.userService.saveUser(postUserDTO);
            return new ResponseEntity<>(postUserDTO, HttpStatus.CREATED);
        } catch (ValidationException | UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id, @RequestParam(required = false, defaultValue = "false") boolean detail){
        try {
            return this.userService.getUserById(id, detail);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<?> getUsers(){
        try {
            return new ResponseEntity<>(this.userService.getUsers(), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserNotDetailsDTO>> search(@RequestParam String name){
        return this.userService.getUserByName(name);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserNotDetailsDTO notDetailsDTO){
        try {
            return this.userService.updateUser(notDetailsDTO);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        try {
            this.userService.deleteById(id);
            return new ResponseEntity<>("Smazáno", HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
