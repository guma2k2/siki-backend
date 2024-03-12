package com.siki.user.dto;

import org.keycloak.representations.idm.UserRepresentation;

public record UserDto (String id, String username, String email, String firstName, String lastName) {
    public static UserDto fromUserRepresentation(UserRepresentation userRepresentation) {
        return new UserDto(userRepresentation.getId(),
                userRepresentation.getUsername(),
                userRepresentation.getEmail(),
                userRepresentation.getFirstName(),
                userRepresentation.getLastName());
    }
}
