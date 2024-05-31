package com.siki.user.dto;

import com.siki.user.model.User;
import org.keycloak.representations.idm.UserRepresentation;

public record UserDto (String id,
                       String username,
                       String email,
                       String firstName,
                       String lastName,
                       String address,
                       String avatar,
                       String phoneNumber,
                       String dateOfBirth
) {
    public static UserDto fromUserRepresentation(UserRepresentation userRepresentation, User user) {
        return new UserDto(userRepresentation.getId(),
                userRepresentation.getUsername(),
                userRepresentation.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getAvatar(),
                user.getPhoneNumber(),
                user.getDateOfBirth().toString()
        );
    }
}
