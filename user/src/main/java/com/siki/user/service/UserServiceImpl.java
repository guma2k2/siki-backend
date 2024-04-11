package com.siki.user.service;

import com.siki.user.dto.CustomerPostDto;
import com.siki.user.dto.CustomerProfileRequest;
import com.siki.user.dto.UserDto;
import com.siki.user.exception.AccessDeniedException;
import com.siki.user.utils.Constants;
import jakarta.ws.rs.BadRequestException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    //    private final Keycloak keycloak;
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String adminClientId;
    private final static String CUSTOMER = "CUSTOMER";

    private final Keycloak keycloak;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }


    @Override
    public UserDto createCustomer(CustomerPostDto customerPostDto) {
        try {
//           log.info(realm);
            // Todo: check email is existed
            RealmResource resource = keycloak.realm(realm);
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

                userResource.roles().realmLevel().add(Collections.singletonList(guestRealmRole));

                return UserDto.fromUserRepresentation(user);
            }
            return null;
        } catch (ForbiddenException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public UserDto getCustomerProfile(String customerId) {
        try {
            UserRepresentation userRepresentation = keycloak.realm(realm).users().get(customerId).toRepresentation();
            return UserDto.fromUserRepresentation(userRepresentation);
        }catch (ForbiddenException ex) {
            throw new AccessDeniedException(String.format(Constants.ERROR_CODE.ACCESS_DENIED_ERROR_FORMAT,
                    ex.getMessage(), adminClientId));
        }
    }

    @Override
    public UserDto updateCustomer(CustomerProfileRequest customerProfileRequest) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(customerId).toRepresentation();
        if (userRepresentation != null) {
            RealmResource resource = keycloak.realm(realm);
            UserResource userResource = resource.users().get(customerId);

            userRepresentation.setFirstName(customerProfileRequest.firstName());
            userRepresentation.setLastName(customerProfileRequest.lastName());
            userRepresentation.setEmail(customerProfileRequest.email());

            if (customerProfileRequest.password() != null) {
                userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(customerProfileRequest.password())));
            }
            try {
                userResource.update(userRepresentation);
            }catch (BadRequestException ex) {
                log.error(ex.toString());
                log.error(ex.getMessage());
            }
            return UserDto.fromUserRepresentation(userRepresentation);
        }  else {
            throw new RuntimeException();
        }
    }
}
