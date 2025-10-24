package com.example.GenesisResourcesBackend.service;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.User;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserNotDetailsDTO;
import com.example.GenesisResourcesBackend.repository.UserRepo;
import com.example.GenesisResourcesBackend.service.certificate.CertificateAuthority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

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
    @ParameterizedTest
    @ValueSource(ints = {1,2})
    @Sql(statements = "alter table users alter column id restart with 1")
    void getUserById(int id) throws UserException {
        this.userRepo.saveAll(List.of(
                new User("David", "Stupen", "rU8nA9eT2bYh", UUID.randomUUID().toString()),
                new User("Jarda", "Jagr", "wV6eH1fK7qZj", UUID.randomUUID().toString()))
        );
        UserNotDetailsDTO notDetailsDTO = (UserNotDetailsDTO) this.userService.getUserById(id, false).getBody();
        assertEquals(id, notDetailsDTO.id());
    }

    @Test
    void getUsers() throws UserException, ValidationException {
        this.userService.saveUser(new PostUserDTO("David", "Stupen", "sL4gN9dC3bXz"));
        List<UserNotDetailsDTO> detailsDTOS = this.userService.getUsers();
        assertFalse(detailsDTOS.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"David", "Filip"})
    void getUserByName(String name) throws ValidationException, UserException {
        this.userService.saveUser(new PostUserDTO(name, "neco", "sL4gN9dC3bXz"));
        this.userService.saveUser(new PostUserDTO(name, "Novotný", "wV6eH1fK7qZj"));
        List<UserNotDetailsDTO> notDetailsDTOS = this.userService.getUserByName(name).getBody();
        assertFalse(notDetailsDTOS.isEmpty());
    }

    @Test
    @Sql(statements = "alter table users alter column id restart with 1")
    void updateUser() throws ValidationException, UserException {
        this.userRepo.save(new User("David", "Stupen", "rU8nA9eT2bYh", UUID.randomUUID().toString()));
        this.userService.updateUser(new UserNotDetailsDTO(1, "Filip", "Novotný"));
        UserNotDetailsDTO notDetailsDTO = (UserNotDetailsDTO) this.userService.getUserById(1, false).getBody();
        assertEquals("Filip", notDetailsDTO.name());
    }

    @Test
    @Sql(statements = "alter table users alter column id restart with 1")
    void deleteById() throws ValidationException, UserException {
        this.userService.saveUser(new PostUserDTO("David", "neco", "sL4gN9dC3bXz"));
        UserNotDetailsDTO notDetailsDTO = this.userService.deleteById(1);
        assertNotNull(notDetailsDTO);
    }
}