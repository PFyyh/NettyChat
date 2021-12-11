package com.start.six.protocol.core.serializable;

public class JSONSerializer implements MessageSerializer{
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }

    @Override
    public <T> byte[] serialize(T object) {
        return new byte[0];
    }
}
