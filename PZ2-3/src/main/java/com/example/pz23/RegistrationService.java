package com.example.pz23;

import com.example.pz23.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class RegistrationService {

    private final KeycloakProperties keycloakProperties;

    public RegistrationService(KeycloakProperties keycloakProperties, Keycloak keycloak) {
        this.keycloakProperties = keycloakProperties;
        this.keycloak = keycloak;
    }

    private final Keycloak keycloak;


    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);
    public void register() {
        log.info("User registered"); // ✔️ тепер працює
    }

    public void registerNewUser(String username, String password, String email) {

        final UserRepresentation user = createUserRepresentation(username, email);
        final CredentialRepresentation credential = createCredentialRepresentation(password);
        user.setCredentials(List.of(credential));
        final UsersResource usersResource = createUsersResource();
        final RoleRepresentation defaultRole = keycloak.realm(keycloakProperties.getRealm())
                .roles()
                .get("app_user")
                .toRepresentation();
        createNewUserInKeycloak(username, usersResource, user)
                .ifPresent(newKeycloackUser -> getUserRoles(usersResource, newKeycloackUser.getId(), newKeycloackUser.getUsername(),
                        defaultRole));
    }

    private static void getUserRoles(UsersResource usersResource, String newUserId,
                                     String username, RoleRepresentation defaultRole) {
        try {
            usersResource.get(newUserId)
                    .roles()
                    .realmLevel()
                    .add(List.of(defaultRole));
        } catch (Exception e) {
            usersResource.get(newUserId).remove();
            throw new RuntimeException(String.format("Не вдалося створити користувача: %s", username), e);
        }
    }

    private static Optional<UserRepresentation> createNewUserInKeycloak(String username, UsersResource usersResource,
                                                                        UserRepresentation user) {
        try (Response response = usersResource.create(user)) {
            if (response.getStatus() != 201) {
                String errorMessage = response.readEntity(String.class);
                log.error("Не вдалося створити користувача: {}", errorMessage);
                throw new RuntimeException("Не вдалося створити користувача: " + errorMessage);
            } else {
                return Optional.ofNullable(usersResource.searchByUsername(username, true))
                        .orElse(List.of())
                        .stream()
                        .findFirst();
            }
        }
    }

    private UsersResource createUsersResource() {
        return Optional.ofNullable(keycloakProperties.getRealm())
                .map(keycloak::realm)
                .map(RealmResource::users)
                .orElseThrow(() -> new RuntimeException("Realm not found: " + keycloakProperties.getRealm()));
    }

    private static CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    private static UserRepresentation createUserRepresentation(String username, String email) {
        final UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}