package com.siki.user.service;

import com.siki.user.config.KeycloakPropsConfig;
import com.siki.user.dto.CustomerPostDto;
import com.siki.user.dto.UserDto;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@Slf4j
public class UserService {
    private final Keycloak keycloak;
    private final KeycloakPropsConfig keycloakPropsConfig;
    private final static String CUSTOMER = "CUSTOMER";

    public UserService(Keycloak keycloak, KeycloakPropsConfig keycloakPropsConfig) {
        this.keycloak = keycloak;
        this.keycloakPropsConfig = keycloakPropsConfig;
    }

    public UserDto createCustomer (
            CustomerPostDto customerPostDto
    ) {
       try {
           // Todo: check email is existed
           RealmResource resource = keycloak.realm(keycloakPropsConfig.getRealm());
           CredentialRepresentation credential = createPasswordCredentials(customerPostDto.password());
           UserRepresentation user = new UserRepresentation();
           user.setFirstName(customerPostDto.firstName());
           user.setLastName(customerPostDto.lastName());
           user.setEmail(customerPostDto.email());
           user.setUsername(customerPostDto.username());
           user.setCredentials(Collections.singletonList(credential));
           user.setEnabled(true);
           Response response = resource.users().create(user);

           // get new user
           if (Objects.equals(response.getStatus(),201)) {
               String userId = CreatedResponseUtil.getCreatedId(response);
               user.setId(userId);
               UserResource userResource = resource.users().get(userId);
               RoleRepresentation guestRealmRole = resource.roles().get(CUSTOMER).toRepresentation();

               // Assign realm role GUEST to user
               userResource.roles().realmLevel().add(Collections.singletonList(guestRealmRole));

               return UserDto.fromUserRepresentation(user);
           }
           return null;
       } catch (ForbiddenException ex) {
           log.error(ex.getMessage());
           return null;
       }

    }
    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
