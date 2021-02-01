package org.step.linked.step.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.step.linked.step.model.User;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SerializationDeserializationProcessor {

    private static final ObjectMapper OBJECT_MAPPER;
    private static final Lock lock;

    static {
        lock = new ReentrantLock();
        lock.lock();
        OBJECT_MAPPER = new ObjectMapper();
        lock.unlock();
    }

    public static <T> String serialize(T t) {
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T deserialize(String json, Class<T> tClass) {
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T deserialize(InputStream io, Class<T> tClass) {
        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(io, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
