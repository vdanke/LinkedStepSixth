package org.step.linked.step.configuration.security.provider.user;

import org.step.linked.step.configuration.security.provider.AuthProvider;
import org.step.linked.step.exception.UnsupportedProviderException;

import java.util.Map;

public class OAuth2UserFactory {

    public OAuth2UserInfo getUserInfoByProvider(String providerId, Map<String, Object> attributes) {
        if (providerId.equalsIgnoreCase(AuthProvider.GOOGLE.name())) {
            return new OAuth2GoogleUserInfo(attributes, providerId);
        }
        if (providerId.equalsIgnoreCase(AuthProvider.FACEBOOK.name())) {
            return new OAuth2FacebookUserInfo(attributes, providerId);
        }
        if (providerId.equalsIgnoreCase(AuthProvider.GITHUB.name())) {
            return new OAuth2GithubUserInfo(attributes, providerId);
        }
        throw new UnsupportedProviderException(String.format("Provider %s not found", providerId));
    }
}
