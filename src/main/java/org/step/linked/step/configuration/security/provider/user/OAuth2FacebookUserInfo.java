package org.step.linked.step.configuration.security.provider.user;

import java.util.Map;

public class OAuth2FacebookUserInfo extends OAuth2UserInfo {

    public OAuth2FacebookUserInfo(Map<String, Object> attributes, String providerId) {
        super(attributes, providerId);
    }

    @Override
    public String getProviderId() {
        return super.getProviderId();
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getGender() {
        return null;
    }

    @Override
    public String getLocale() {
        return null;
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
