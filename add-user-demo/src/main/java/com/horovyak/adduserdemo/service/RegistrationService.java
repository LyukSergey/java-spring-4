package com.horovyak.adduserdemo.service;

import com.horovyak.adduserdemo.properties.KeycloakProperties;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final KeycloakProperties keycloakProperties;
    private final Keycloak keycloak;

    public void registerNewUser(String username, String password, String email) {
        // 1. Create user representation
        final UserRepresentation user = createUserRepresentation(username, email);
        // 2. Create credential representation
        final CredentialRepresentation credential = createCredentialRepresentation(password);
        user.setCredentials(List.of(credential));

        // 3. Get access to user resources for the realm
        final UsersResource usersResource = createUsersResource();
        // 4. Get the role representation to assign
        final RoleRepresentation defaultRole = keycloak.realm(keycloakProperties.getRealm())
                .roles()
                .get("app_user")
                .toRepresentation();

        // 5. Perform atomic operation to create user and assign role
        createNewUserInKeycloak(username, usersResource, user)
                .ifPresent(newKeycloakUser -> getUserRoles(usersResource, newKeycloakUser.getId(), newKeycloakUser.getUsername(), defaultRole));
    }

    // 6. Method to assign role with rollback on exception
    private static void getUserRoles(UsersResource usersResource, String newUserId, String username, RoleRepresentation defaultRole) {
        try {
            // Assign role to user
            usersResource.get(newUserId)
                    .roles()
                    .realmLevel()
                    .add(List.of(defaultRole));
        } catch (Exception e) {
            // Rollback: delete the newly created user if role assignment fails
            usersResource.get(newUserId).remove();
            throw new RuntimeException(String.format("Failed to create user: %s", username), e);
        }
    }

    // 7. Method to create user via Keycloak API
    private static Optional<UserRepresentation> createNewUserInKeycloak(String username, UsersResource usersResource, UserRepresentation user) {
        try (Response response = usersResource.create(user)) {
            if (response.getStatus() != 201) { // 201 Created
                String errorMessage = response.readEntity(String.class);
                log.error("Failed to create user: {}", errorMessage);
                throw new RuntimeException("Failed to create user: " + errorMessage);
            } else {
                // Return the newly created user for further operations
                return Optional.ofNullable(usersResource.searchByUsername(username, true))
                        .orElse(List.of())
                        .stream()
                        .findFirst();
            }
        }
    }

    // 8. Method to get user resource
    private UsersResource createUsersResource() {
        return Optional.ofNullable(keycloakProperties.getRealm())
                .map(keycloak::realm)
                .map(RealmResource::users)
                .orElseThrow(() -> new RuntimeException("Realm not found: " + keycloakProperties.getRealm()));
    }

    // 9. Method to create credential representation
    private static CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    // 10. Method to create user representation
    private static UserRepresentation createUserRepresentation(String username, String email) {
        final UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}