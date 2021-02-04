package org.step.linked.step.configuration.security;

public class SecurityConstants {

    public static final String PROVIDER_ID = "sub";
    public static final String PROVIDER_NAME = "name";
    public static final String PROVIDER_EMAIL = "email";
    public static final String PROVIDER_GENDER = "gender";
    public static final String PROVIDER_LOCALE = "locale";
    public static final String PROVIDER_PICTURE = "picture";
    public static final String OAUTH2_AUTHORIZATION_ENDPOINT = "/oauth2/authorize";
    public static final String OAUTH2_REDIRECT_ENDPOINT = "/oauth2/callback/*";
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String OAUTH2_DEFAULT_REDIRECT_URI = "/success";
    public static final String OAUTH2_REDIRECT_COOKIE_NAME = "oauth2_redirect";
    public static final int OAUTH2_COOKIE_MAX_AGE = 36000000;
    public static final String REGISTRATION_ID = "registration_id";
}
