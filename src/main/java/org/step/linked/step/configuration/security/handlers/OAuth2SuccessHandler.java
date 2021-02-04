package org.step.linked.step.configuration.security.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.step.linked.step.configuration.security.provider.user.OAuth2UserFactory;
import org.step.linked.step.configuration.security.provider.user.OAuth2UserInfo;
import org.step.linked.step.configuration.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import org.step.linked.step.exception.AuthenticationProviderException;
import org.step.linked.step.model.Authorities;
import org.step.linked.step.model.User;
import org.step.linked.step.repository.UserRepositoryImpl;
import org.step.linked.step.service.CookieOperationService;
import org.step.linked.step.service.JwtService;
import org.step.linked.step.service.SerializationDeserializationService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.step.linked.step.configuration.security.SecurityConstants.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository requestRepository;
    private final CookieOperationService cookieOperationService;
    private final SerializationDeserializationService<OAuth2AuthorizationRequest, String> serializationDeserializationService;
    private final UserRepositoryImpl userRepository;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUri = this.determineTargetUrl(request, response, authentication);

        int indexToSplit = redirectUri.indexOf("?");

        String redirect = redirectUri.substring(0, indexToSplit);
        String tokenQueryParam = redirectUri.substring(indexToSplit + 1);
        String token = tokenQueryParam.split("=")[1];

        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);

        if (response.isCommitted()) {
            log.warn("Response already committed");
            return;
        }
        this.clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, redirect);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();

        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = cookieOperationService.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(Cookie::getValue)
                .map(value -> serializationDeserializationService.deserialize(value, OAuth2AuthorizationRequest.class))
                .orElseThrow(() -> new AuthenticationProviderException("Cookie is not present in success handler"));

        OAuth2UserInfo oAuth2UserInfo = getOAuth2UserByProvider(oAuth2AuthorizationRequest, principal);

        String email = oAuth2UserInfo.getEmail();

        Optional<User> userByUsername = userRepository.findByUsername(email);

        User user = userByUsername
                .map(value -> this.updateExistUser(value, oAuth2UserInfo))
                .orElseGet(() -> this.saveNewUser(oAuth2UserInfo));

        User savedUser = userRepository.save(user);

        String token = jwtService.createToken(savedUser);

        String redirectUri = cookieOperationService.getCookie(request, OAUTH2_REDIRECT_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("/success");

        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(5050)
                .path(String.format("/oauth2/%s", redirectUri))
                .queryParam("token", token)
                .build()
                .toUriString();
    }

    private User saveNewUser(OAuth2UserInfo oAuth2UserInfo) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .authoritiesList(Collections.singleton(Authorities.ROLE_USER))
                .providers(Collections.singleton(oAuth2UserInfo.getProviderId()))
                .username(oAuth2UserInfo.getEmail())
                .fullName(oAuth2UserInfo.getName())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .gender(oAuth2UserInfo.getGender())
                .locale(oAuth2UserInfo.getLocale())
                .build();
    }

    private User updateExistUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setLocale(oAuth2UserInfo.getLocale());
        user.setGender(oAuth2UserInfo.getGender());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        user.getProviders().add(oAuth2UserInfo.getProviderId());
        user.setFullName(oAuth2UserInfo.getName());
        return user;
    }

    private OAuth2UserInfo getOAuth2UserByProvider(OAuth2AuthorizationRequest oAuth2AuthorizationRequest, DefaultOidcUser principal) {
        Map<String, Object> requestAttributes = oAuth2AuthorizationRequest.getAttributes();
        Map<String, Object> attributes = principal.getAttributes();

        String provider = (String) requestAttributes.get(REGISTRATION_ID);

        return OAuth2UserFactory.getUserInfoByProvider(provider, attributes);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        requestRepository.removeAuthorizationRequest(request, response);
    }
}
