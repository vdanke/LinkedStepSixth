package org.step.linked.step.service;

import java.io.InputStream;

public interface SerializationDeserializationService<T, Z> {

    T deserialize(Z z, Class<T> tClass);

    Z serialize(T t);

    T deserialize(InputStream io, Class<T> tClass);
}
