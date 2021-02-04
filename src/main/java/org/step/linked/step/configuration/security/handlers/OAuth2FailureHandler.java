package org.step.linked.step.configuration.security.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import org.step.linked.step.configuration.security.repository.HttpCookieOAuth2AuthorizationRequestRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository requestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String redirectUri = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(5050)
                .path("/oauth2/error")
                .queryParam("message", e.getLocalizedMessage())
                .build()
                .toUriString();

        requestRepository.removeAuthorizationRequest(request, response);

        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
}
