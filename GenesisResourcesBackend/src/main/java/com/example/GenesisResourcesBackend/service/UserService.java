package com.example.GenesisResourcesBackend.service;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.User;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserDetailsDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserNotDetailsDTO;
import com.example.GenesisResourcesBackend.repository.UserRepo;
import com.example.GenesisResourcesBackend.service.certificate.CertificateAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private UserRepo userRepo;
    private CertificateAuthority certificateAuthority;

    public UserService(UserRepo userRepo, CertificateAuthority certificateAuthority) {
        this.userRepo = userRepo;
        this.certificateAuthority = certificateAuthority;
    }

    public void saveUser(PostUserDTO postUserDTO) throws ValidationException, UserException {
        if (this.certificateAuthority.validationPersonId(postUserDTO.personId())){
            User user = this.userRepo.findByPersonId(postUserDTO.personId()).orElse(null);
            if (user != null)
                throw new UserException("uživatel s personId " + postUserDTO.personId() + " již existuje. Kontaktujte prosím nás pro udělení nového personId.");
            String uuid = UUID.randomUUID().toString();
            User userForDB = new User(postUserDTO.name(), postUserDTO.surname(), postUserDTO.personId(), uuid);
            this.userRepo.save(userForDB);
        }
    }

    public ResponseEntity<?> getUserById(int id, boolean detail) throws UserException {
        User user = this.userRepo.findById(id).orElseThrow(() -> new UserException("zadané id: " + id + " neexistuje!!!!"));
        if (!detail) {
            UserNotDetailsDTO notDetails = new UserNotDetailsDTO(user.getId(), user.getName(), user.getSurname());
            return new ResponseEntity<>(notDetails, HttpStatus.OK);
        }
        UserDetailsDTO details = new UserDetailsDTO(user.getId(), user.getName(), user.getSurname(), user.getPersonId(), user.getUuid());
        return new ResponseEntity<>(details, HttpStatus.OK);
    }
}
