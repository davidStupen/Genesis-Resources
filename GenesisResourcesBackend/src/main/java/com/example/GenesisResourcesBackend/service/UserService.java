package com.example.GenesisResourcesBackend.service;

import com.example.GenesisResourcesBackend.exception.UserException;
import com.example.GenesisResourcesBackend.exception.ValidationException;
import com.example.GenesisResourcesBackend.modul.User;
import com.example.GenesisResourcesBackend.modul.dto.PostUserDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserDetailsDTO;
import com.example.GenesisResourcesBackend.modul.dto.UserNotDetailsDTO;
import com.example.GenesisResourcesBackend.repository.UserRepo;
import com.example.GenesisResourcesBackend.service.certificate.CertificateAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private UserRepo userRepo;
    private CertificateAuthority certificateAuthority;

    public UserService(UserRepo userRepo, CertificateAuthority certificateAuthority) {
        this.userRepo = userRepo;
        this.certificateAuthority = certificateAuthority;
    }

    public PostUserDTO saveUser(PostUserDTO postUserDTO) throws ValidationException, UserException {
        if (postUserDTO.name() == null)
            throw new UserException("Jmeno je null");
        if (postUserDTO.personId() == null)
            throw new UserException("přijmení je null");
        if (this.certificateAuthority.validationPersonId(postUserDTO.personId())){
            User user = this.userRepo.findByPersonId(postUserDTO.personId()).orElse(null);
            if (user != null)
                throw new UserException("uživatel s personId " + postUserDTO.personId() + " již existuje. Kontaktujte prosím nás pro udělení nového personId.");
            String uuid = UUID.randomUUID().toString();
            User userForDB = new User(postUserDTO.name(), postUserDTO.surname(), postUserDTO.personId(), uuid);
            this.userRepo.save(userForDB);
            return postUserDTO;
        }
        return null;
    }

    public ResponseEntity<?> getUserById(int id, boolean detail) throws UserException {
        User user = this.userRepo.findById(id).orElseThrow(() -> new UserException("zadané id: " + id + " neexistuje!!!!"));
        if (!detail) {
            UserNotDetailsDTO notDetails = new UserNotDetailsDTO(user.getId(), user.getName(), user.getSurname());
            log.info("select jednoho uživatele " + notDetails);
            return new ResponseEntity<>(notDetails, HttpStatus.OK);
        }
        UserDetailsDTO details = new UserDetailsDTO(user.getId(), user.getName(), user.getSurname(), user.getPersonId(), user.getUuid());
        log.info("select jednoho uživatele " + details);
        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    public List<UserNotDetailsDTO> getUsers() throws UserException {
        if (this.userRepo.count() == 0)
            throw new UserException("sezname uživatelů je prázný");
        List<User> users = this.userRepo.findAll();
        return users.stream().map(item -> new UserNotDetailsDTO(
                item.getId(), item.getName(), item.getSurname()
        )).toList();
    }
    public ResponseEntity<List<UserNotDetailsDTO>> getUserByName(String name) {
        List<User> users = this.userRepo.findByNameContaining(name);
        if ( ! users.isEmpty() ){
            List<UserNotDetailsDTO> notDetailsDTO = users.stream()
                    .map(item -> new UserNotDetailsDTO(
                            item.getId(), item.getName(), item.getSurname()
                    )).toList();
            return new ResponseEntity<>(notDetailsDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of(), HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<?> updateUser(UserNotDetailsDTO notDetailsDTO) throws UserException {
        UserDetailsDTO detailsDTO = (UserDetailsDTO) this.getUserById(notDetailsDTO.id(), true).getBody();
        User user = new User();
        user.setId(notDetailsDTO.id());
        user.setName(notDetailsDTO.name());
        user.setSurname(notDetailsDTO.surname());
        user.setPersonId(detailsDTO.personId());
        user.setUuid(detailsDTO.uuid());
        this.userRepo.save(user);
        return new ResponseEntity<>(notDetailsDTO, HttpStatus.OK);
    }

    public UserNotDetailsDTO deleteById(int id) throws UserException {
        UserNotDetailsDTO notDetailsDTO = (UserNotDetailsDTO) this.getUserById(id, false).getBody();
        userRepo.deleteById(id);
        return notDetailsDTO;
    }

}
