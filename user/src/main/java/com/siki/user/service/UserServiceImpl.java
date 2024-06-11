package com.siki.user.service;

import com.siki.user.dto.CustomerPostDto;
import com.siki.user.dto.CustomerProfileRequest;
import com.siki.user.dto.UserDto;
import com.siki.user.exception.AccessDeniedException;
import com.siki.user.exception.BadRequestException;
import com.siki.user.model.User;
import com.siki.user.repository.UserRepository;
import com.siki.user.utils.Constants;
import com.siki.user.utils.DateUtils;
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
import java.util.List;
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

    private final static String ADMIN = "ADMIN";
    private final Keycloak keycloak;
    private final UserRepository userRepository;

    public UserServiceImpl(Keycloak keycloak, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
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
            if (customerPostDto.email().isBlank() || customerPostDto.email().isEmpty()) {
                throw new BadRequestException("Email không được bỏ trống");
            }

            if (userRepository.findByEmail(customerPostDto.email()).isPresent()) {
                throw new BadRequestException("Email đã tồn tại");
            }

            if (userRepository.findByPhoneNumber(customerPostDto.phoneNumber()).isPresent()) {
                throw new BadRequestException("Sdt đã tồn tại");
            }


            RealmResource resource = keycloak.realm(realm);
            CredentialRepresentation credential = createPasswordCredentials(customerPostDto.password());
            UserRepresentation user = new UserRepresentation();
            user.setEmail(customerPostDto.email());
            user.setFirstName(customerPostDto.firstName());
            user.setLastName(customerPostDto.lastName());
            if (customerPostDto.username() == null) {
                user.setUsername(customerPostDto.email());
            }else {
                user.setUsername(customerPostDto.username());
            }

            user.setCredentials(Collections.singletonList(credential));
            user.setEnabled(true);
            Response response = resource.users().create(user);

            log.info(response.getStatus() + "");
            // get new user
            if (Objects.equals(response.getStatus(),201)) {
                String userId = CreatedResponseUtil.getCreatedId(response);

                User customer = User.builder()
                        .id(userId)
                        .firstName(customerPostDto.firstName())
                        .lastName(customerPostDto.lastName())
                        .address(customerPostDto.address())
                        .email(customerPostDto.email())
                        .phoneNumber(customerPostDto.phoneNumber())
                        .dateOfBirth(DateUtils.convertToLocalDateTime(customerPostDto.dateOfBirth()))
                        .build();
                userRepository.save(customer);
                user.setId(userId);
                UserResource userResource = resource.users().get(userId);
                RoleRepresentation guestRealmRole = resource.roles().get(CUSTOMER).toRepresentation();

                userResource.roles().realmLevel().add(Collections.singletonList(guestRealmRole));

                return UserDto.fromUserRepresentation(user, customer, CUSTOMER);
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
            /*List<String> realmRoles = userRepresentation.getRealmRoles();
            S

            log.info(realmRoles.toString());*/

            List<String> roles = keycloak.realm(realm).users().get(customerId).roles().realmLevel().listEffective().stream()
                    .map(roleRepresentation -> roleRepresentation.getName())
                    .toList();
            String role = null;
            for (String r : roles) {
                if (r.equals("ADMIN")) {
                    role = "ADMIN";
                    break; // Exit loop once role is set
                } else if (r.equals("CUSTOMER")) {
                    role = "CUSTOMER";
                    // Continue checking in case the user has multiple roles and "ADMIN" is preferred
                }
            }
            if (role == null) {
                role = "";
            }
            User user = userRepository.findById(customerId).orElseThrow();
            return UserDto.fromUserRepresentation(userRepresentation, user, role);
        }catch (ForbiddenException ex) {
            throw new AccessDeniedException(String.format(Constants.ERROR_CODE.ACCESS_DENIED_ERROR_FORMAT,
                    ex.getMessage(), adminClientId));
        }
    }

    @Override
    public UserDto updateCustomer(CustomerProfileRequest customerProfileRequest) {
        String customerId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserRepresentation userRepresentation = keycloak.realm(realm).users().get(customerId).toRepresentation();
        if (customerProfileRequest.email() != null) {
            if (customerProfileRequest.email() != userRepresentation.getEmail()) {
                if (userRepository.findByEmail(customerProfileRequest.email()).isPresent()) {
                    throw new BadRequestException("Email đã tồn tại");
                }
            }
        }
        if (userRepresentation != null) {
            RealmResource resource = keycloak.realm(realm);
            UserResource userResource = resource.users().get(customerId);
            userRepresentation.setEmail(customerProfileRequest.email());
            User oldUser = userRepository.findById(customerId).orElseThrow();
            oldUser.setFirstName(customerProfileRequest.firstName());
            oldUser.setLastName(customerProfileRequest.lastName());
            oldUser.setAddress(customerProfileRequest.address());
            oldUser.setPhoneNumber(customerProfileRequest.phoneNumber());
            if (customerProfileRequest.avatar() != null && !customerProfileRequest.avatar().isBlank()) {
                oldUser.setAvatar(customerProfileRequest.avatar());
            }
            oldUser.setDateOfBirth(DateUtils.convertToLocalDateTime(customerProfileRequest.dateOfBirth()));
            oldUser.setEmail(customerProfileRequest.email());
            userRepository.save(oldUser);

            if (customerProfileRequest.password() != null) {
                userRepresentation.setCredentials(Collections.singletonList(createPasswordCredentials(customerProfileRequest.password())));
            }
            userResource.update(userRepresentation);
            return UserDto.fromUserRepresentation(userRepresentation, oldUser, CUSTOMER);
        }  else {
            throw new BadRequestException("Khong the update user");
        }
    }
}
