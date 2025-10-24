package com.example.GenesisResourcesBackend.service;

import com.example.GenesisResourcesBackend.modul.User;
import com.example.GenesisResourcesBackend.repository.UserRepo;
import com.example.GenesisResourcesBackend.service.certificate.CertificateAuthority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class UserServiceTest {
    @Autowired
    private UserRepo userRepo;
    private UserService userService;
    private CertificateAuthority authority;
    @BeforeEach
    void setUp() {
        this.authority = new CertificateAuthority();
        this.userService = new UserService(this.userRepo, this.authority);
    }
//      rU8nA9eT2bYh
//      wV6eH1fK7qZj
//      sL4gN9dC3bXz
    //DataIntegrityViolationException
    @Test
    void saveUserTrue() {
        this.userRepo.saveAll(List.of(
                new User("David", "Stupen", "rU8nA9eT2bYh", UUID.randomUUID().toString()),
                new User("Jarda", "Jagr", "wV6eH1fK7qZj", UUID.randomUUID().toString())
        ));
        assertTrue(this.userRepo.count() > 0);
    }
    @Test
    void saveThrowException(){
        List<User> users = Arrays.asList(
                new User("David", "Stupen", "rU8nA9eT2bYh", UUID.randomUUID().toString()),
                new User("Jarda", "Jagr", "rU8nA9eT2bYh", UUID.randomUUID().toString()));
        assertThrows(DataIntegrityViolationException.class, () -> this.userRepo.saveAll(users));
    }
    @Test
    void getUserById() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void getUserByName() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteById() {
    }
}