package com.lss.l1springbootsecurityspaaddnewuser.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak.admin-client")
public class KeycloakProperties {

    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
}
