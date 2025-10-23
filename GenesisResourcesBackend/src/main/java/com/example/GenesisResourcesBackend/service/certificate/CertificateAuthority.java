package com.example.GenesisResourcesBackend.service.certificate;

import com.example.GenesisResourcesBackend.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Component
public class CertificateAuthority {
    private static final String PATH = "src/main/resources/dataPersonId.txt";

    public boolean validationPersonId(String personId) throws ValidationException {
        if (personId.length() != 12)
            throw new ValidationException("déka personId musí být 12 a je " + personId.length() + ", PersonId: " + personId + ". Kontaktujte Jacka pro správné person id");
        File fileTxt = new File(PATH);
        List<String> filePersonIdFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileTxt))){
            while (reader.ready()){
                filePersonIdFile.add(reader.readLine());
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("soubor nebyl nalezen: " + e.getMessage());
        } catch (IOException e) {
            throw new IllegalArgumentException("něco se pokazilo se souborem: " + e.getMessage());
        }
        boolean con = false;
        for (String isValid : filePersonIdFile){
            if (isValid.equals(personId)) {
                con = true;
                break;
            }
        }
        if ( ! con )
            throw new ValidationException("Toto personId není povoleno, Zadané personId: " + personId +  ". Kontaktujte Jacka pro správné person id");
        return con;
    }
}
