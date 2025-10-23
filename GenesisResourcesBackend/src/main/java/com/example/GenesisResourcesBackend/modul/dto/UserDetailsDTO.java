package com.example.GenesisResourcesBackend.modul.dto;

public record UserDetailsDTO(
        int id,
        String name,
        String surname,
        String personId,
        String uuid
) {
}
