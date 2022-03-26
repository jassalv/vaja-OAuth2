package client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizationCodeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientOauth2Config {

    @Value("${spring.resource-uri:http://localhost:18080/time}")
    String resourceUri;

    @Bean
    public WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        return WebClient.builder().baseUrl(this.resourceUri).filter(oauth).build();
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);

        authorizedClientManager.setAuthorizedClientProvider(new AuthorizationCodeOAuth2AuthorizedClientProvider());

        return authorizedClientManager;
    }

    // either enable the bean below or spring will create ClientRegistrationRepository based on the application
    // properties/yml
    // @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.oktaClientRegistration());
    }

    private ClientRegistration oktaClientRegistration() {
        return ClientRegistration.withRegistrationId("okta")
                .clientId("0oa1pr1alclORk0g7357")
                .clientSecret("5FNu9pvlU4oW1KCnqJV4LjhHeffj_ditgP1ZNlpX")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8888/login/oauth2/code/okta")
                .scope("openid", "offline_access", "accesstime") // api scopes requested
                .authorizationUri("https://dev-563628.okta.com/oauth2/default/v1/authorize")
                .tokenUri("https://dev-563628.okta.com/oauth2/default/v1/token")
                .userInfoUri("https://dev-563628.okta.com/oauth2/default/v1/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .issuerUri("https://dev-563628.okta.com/oauth2/default")
                .jwkSetUri("https://dev-563628.okta.com/oauth2/default/v1/keys")
                .clientName("Okta")
                .build();
    }
}
