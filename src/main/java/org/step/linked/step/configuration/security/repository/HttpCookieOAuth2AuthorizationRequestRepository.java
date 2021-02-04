package org.step.linked.step.configuration.security.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;
import org.step.linked.step.exception.AuthenticationProviderException;
import org.step.linked.step.service.CookieOperationService;
import org.step.linked.step.service.SerializationDeserializationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.step.linked.step.configuration.security.SecurityConstants.*;

@Component
@RequiredArgsConstructor
public class HttpCookieOAuth2AuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final CookieOperationService cookieOperationService;
    private final SerializationDeserializationService<OAuth2AuthorizationRequest, String> serializationDeserializationService;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return cookieOperationService.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(Cookie::getValue)
                .map(oauth2RequestValue -> serializationDeserializationService.deserialize(oauth2RequestValue, OAuth2AuthorizationRequest.class))
                .orElseThrow(() -> new AuthenticationProviderException("Failed to load authorization request, cookie is empty"));
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest oAuth2AuthorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (oAuth2AuthorizationRequest == null) {
            this.removeAuthorizationRequestCookie(request, response);
            throw new AuthenticationProviderException("OAuth2 authorization request is null");
        }
        String oauth2RequestValue = serializationDeserializationService.serialize(oAuth2AuthorizationRequest);

        String redirectParam = request.getParameter("redirect");

        cookieOperationService.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, oauth2RequestValue, OAUTH2_COOKIE_MAX_AGE);

        if (redirectParam == null || redirectParam.length() == 0) {
            cookieOperationService.addCookie(response, OAUTH2_REDIRECT_COOKIE_NAME, OAUTH2_DEFAULT_REDIRECT_URI, OAUTH2_COOKIE_MAX_AGE);
        } else {
            cookieOperationService.addCookie(response, OAUTH2_REDIRECT_COOKIE_NAME, redirectParam, OAUTH2_COOKIE_MAX_AGE);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    private void removeAuthorizationRequestCookie(HttpServletRequest request, HttpServletResponse response) {
        cookieOperationService.deleteCookie(request, response, OAUTH2_REDIRECT_COOKIE_NAME);
        cookieOperationService.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    }
}
