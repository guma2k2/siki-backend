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

public interface UserService {

    UserDto createCustomer(CustomerPostDto customerPostDto);
    UserDto getCustomerProfile(String customerId);
    UserDto updateCustomer(CustomerProfileRequest customerProfileRequest);


}
