package org.step.linked.step.configuration.security.provider.user;

import java.util.Map;

import static org.step.linked.step.configuration.security.SecurityConstants.*;

public class OAuth2GoogleUserInfo extends OAuth2UserInfo {

    public OAuth2GoogleUserInfo(Map<String, Object> attributes, String providerId) {
        super(attributes, providerId);
    }

    @Override
    public String getId() {
        return (String) attributes.get(PROVIDER_ID);
    }

    @Override
    public String getName() {
        return (String) attributes.get(PROVIDER_NAME);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get(PROVIDER_EMAIL);
    }

    @Override
    public String getGender() {
        return (String) attributes.get(PROVIDER_GENDER);
    }

    @Override
    public String getLocale() {
        return (String) attributes.get(PROVIDER_LOCALE);
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get(PROVIDER_PICTURE);
    }
}
