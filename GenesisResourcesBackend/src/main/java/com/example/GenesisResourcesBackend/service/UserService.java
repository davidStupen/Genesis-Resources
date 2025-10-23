package com.example.GenesisResourcesBackend.service;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.User;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.repository.UserRepo;
import com.example.GenesisResourcesBackend.service.certificate.CertificateAuthority;
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
}
