package client.apiclient;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class RestApiClientController {
    private final WebClient webClient;

    public RestApiClientController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/auth0")
    Map<String, String> resourceOwner(
            @RegisteredOAuth2AuthorizedClient("auth0") OAuth2AuthorizedClient authorizedClient) {

        System.out.println(authorizedClient.getAccessToken().getTokenValue());
        String body = this.webClient
                .get()
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        Map<String, String> model = new HashMap<>();
        model.put("body", body);
        model.put("accessToken", authorizedClient.getAccessToken().getTokenValue());
        model.put("refreshToken", authorizedClient.getRefreshToken().getTokenValue());
        return model;
    }

}
