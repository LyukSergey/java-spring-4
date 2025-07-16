package Service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import properties.KeycloakProperties;
import   org.springframework.stereotype.Service ;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final KeycloakProperties keycloakProperties;
    private final Keycloak keycloak;

    public void registerNewUser(String username, String password, String email) {
        // 1. Створюємо представлення нового користувача
        final UserRepresentation user = createUserRepresentation(username, email);
        // 2. Створюємо представлення пароля
        final CredentialRepresentation credential = createCredentialRepresentation(password);
        user.setEmail(List.of(credential).toString());

        // 3. Отримуємо доступ до ресурсів користувачів нашого realm'у
        final UsersResource usersResource = createUsersResource();
        // 4. Отримуємо представлення ролі, яку будемо присвоювати
        final RoleRepresentation defaultRole = keycloak.realm(keycloakProperties.getRealm())
                .roles()
                .get("app_user")
                .toRepresentation();

        // 5. Виконуємо атомарну операцію створення користувача та присвоєння ролі
        createNewUserInKeycloacl(username, usersResource, user)
                .ifPresent(newKeycloackUser -> getUserRoles(usersResource, newKeycloackUser.getId(), newKeycloackUser.getUsername(), defaultRole));
    }

    // 6. Метод присвоєння ролі ролбеком у випадку ексепшину
    private static void getUserRoles(UsersResource usersResource, String newUserId, String username, RoleRepresentation defaultRole) {
        try {
            // Присвоюємо роль користувачу
            usersResource.get(newUserId)
                    .roles()
                    .realmLevel()
                    .add(List.of(defaultRole));
        } catch (Exception e) {
            // ролбек: якщо присвоїти роль не вдалося, видаляємо щойно створеного користувача.
            usersResource.get(newUserId).remove();
            throw new RuntimeException(String.format("Не вдалося створити користувача: %s", username), e);
        }
    }

    // 7. Метод для створення користувача через Keycloak API
    private static Optional<org.keycloak.representations.idm.UserRepresentation> createNewUserInKeycloacl(String username, UsersResource usersResource, UserRepresentation user) {
        try (Response response = usersResource.create(user )) {
            if (response.getStatus() != 201) { // 201 Created
                String errorMessage = response.readEntity(String.class);
                log.error("Не вдалося створити користувача: {}", errorMessage);
                throw new RuntimeException("Не вдалося створити користувача: " + errorMessage);
            } else {
                // Повертаємо щойно створеного користувача для подальших операцій
                return Optional.ofNullable(usersResource.searchByUsername(username, true))
                        .orElse(List.of())
                        .stream()
                        .findFirst();
            }
        }
    }

    // 8. Метод для отримання ресурсу користувачів
    private UsersResource createUsersResource() {
        return Optional.ofNullable(keycloakProperties.getRealm())
                .map(keycloak::realm)
                .map(RealmResource::users)
                .orElseThrow(() -> new RuntimeException("Realm not found: " + keycloakProperties.getRealm()));
    }

    // 9. Метод для створення представлення пароля
    private static CredentialRepresentation createCredentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        return credential;
    }

    // 10. Метод для створення представлення користувача
    private static UserRepresentation createUserRepresentation(String username, String email) {
        final UserRepresentation user = new UserRepresentation();
        user.setEmail(String.valueOf(true));
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}
