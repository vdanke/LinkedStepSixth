package org.step.linked.step.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.step.linked.step.model.User;
import org.step.linked.step.service.SerializationDeserializationService;

import java.io.InputStream;

@Service
@Slf4j
public class JsonSerializationDeserializationImpl implements SerializationDeserializationService<User, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public User deserialize(String s, Class<User> userClass) {
        User user = null;
        try {
            user = objectMapper.readValue(s, userClass);
        } catch (JsonProcessingException e) {
            log.error(String.format("Error during deserialize JSON %s", e.getLocalizedMessage()));
        }
        return user;
    }

    @Override
    public String serialize(User user) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            log.error(String.format("Error during serialize JSON %s", e.getLocalizedMessage()));
        }
        return json;
    }

    @Override
    public User deserialize(InputStream io, Class<User> userClass) {
        User user = null;
        try {
            user = objectMapper.readValue(io, userClass);
        } catch (Exception e) {
            log.error(String.format("Error during deserialize JSON %s", e.getLocalizedMessage()));
        }
        return user;
    }
}
