package com.example.registration;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    @Value("${keycloak.admin-client.server-url}")
    private String serverUrl;
    @Value("${keycloak.admin-client.realm}")
    private String realm;
    @Value("${keycloak.admin-client.client-id}")
    private String clientId;
    @Value("${keycloak.admin-client.client-secret}")
    private String clientSecret;

    public void registerNewUser(String username, String password) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType("client_credentials")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        user.setCredentials(List.of(credential));

        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            String errorMessage = response.readEntity(String.class);
            log.error("Не вдалося створити користувача: {}", errorMessage);
            throw new RuntimeException("Не вдалося створити користувача: " + errorMessage);
        }
        log.info("Користувача '{}' успішно створено.", username);

        // Автоматично присвоюємо роль 'app_user'
        String userId = usersResource.searchByUsername(username, true).get(0).getId();
        RoleRepresentation userRole = keycloak.realm(realm).roles().get("app_user").toRepresentation();
        usersResource.get(userId).roles().realmLevel().add(List.of(userRole));
        log.info("Роль 'app_user' присвоєно користувачу '{}'", username);
    }
}
