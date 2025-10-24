package com.example.GenesisResourcesBackend.service;

import com.example.GenesisResourcesBackend.repository.UserRepo;
import com.example.GenesisResourcesBackend.service.certificate.CertificateAuthority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    @Test
    void saveUser() {
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