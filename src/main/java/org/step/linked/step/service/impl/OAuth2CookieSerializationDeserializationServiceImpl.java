package org.step.linked.step.service.impl;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.step.linked.step.service.SerializationDeserializationService;

import java.io.InputStream;
import java.util.Base64;

@Service
public class OAuth2CookieSerializationDeserializationServiceImpl implements SerializationDeserializationService<OAuth2AuthorizationRequest, String> {

    @Override
    public OAuth2AuthorizationRequest deserialize(String oauth2String, Class<OAuth2AuthorizationRequest> oAuth2AuthorizationRequestClass) {
        byte[] oauth2Bytes = Base64.getDecoder().decode(oauth2String);

        Object oauth2Obj = SerializationUtils.deserialize(oauth2Bytes);

        return oAuth2AuthorizationRequestClass.cast(oauth2Obj);
    }

    @Override
    public String serialize(OAuth2AuthorizationRequest oAuth2AuthorizationRequest) {
        byte[] oauth2Bytes = SerializationUtils.serialize(oAuth2AuthorizationRequest);

        return Base64.getEncoder().encodeToString(oauth2Bytes);
    }

    @Override
    public OAuth2AuthorizationRequest deserialize(InputStream io, Class<OAuth2AuthorizationRequest> oAuth2AuthorizationRequestClass) {
        return null;
    }
}
