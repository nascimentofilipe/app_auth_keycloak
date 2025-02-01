package br.com.filipesoares.app_auth_keycloak.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/token")
@RestController
@EnableWebSecurity
@EnableMethodSecurity
public class TokenController {


    @PostMapping("/")
    public ResponseEntity<String> token(@RequestBody User user) {

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", user.clientId);
        formData.add("username", user.username);
        formData.add("password", user.password);
        formData.add("grant_type", user.grantType);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
        var result = restTemplate.postForEntity("http://localhost:8080/realms/youtube/protocol/openid-connect/token", entity, String.class);

        return result;
    }

    public record User(String password, String clientId, String grantType, String username) {

    }
}
