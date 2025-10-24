package com.example.GenesisResourcesBackend.controller;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserNotDetailsDTO;
import com.example.GenesisResourcesBackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody PostUserDTO postUserDTO) {
        try {
            this.userService.saveUser(postUserDTO);
            log.info("uživatel byl přidán");
            return new ResponseEntity<>(postUserDTO, HttpStatus.CREATED);
        } catch (ValidationException | UserException e) {
            log.error("při ukladání" + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e){
            log.error("při ukladání" + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            List<UserNotDetailsDTO> users = this.userService.getUsers();
            log.info(users.toString());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (UserException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserNotDetailsDTO>> search(@RequestParam String name){
        List<UserNotDetailsDTO> notDetailsDTOS = this.userService.getUserByName(name).getBody();
        log.info(notDetailsDTOS.toString());
        return this.userService.getUserByName(name);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserNotDetailsDTO notDetailsDTO){
        try {
            log.info(notDetailsDTO.toString());
            return this.userService.updateUser(notDetailsDTO);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        try {
            UserNotDetailsDTO user = this.userService.deleteById(id);
            log.info(user.toString());
            return new ResponseEntity<>("Smazáno", HttpStatus.OK);
        } catch (UserException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
