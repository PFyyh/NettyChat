package com.start.six.protocol.core.serializable;

import com.start.six.protocol.core.constant.SerializedTypeConstant;

public class JDKSerializer implements MessageSerializer {

    private static final byte serializedType = SerializedTypeConstant.JDK_SERIALIZABLE;

    @Override
    public byte getSerializedType(){
        return serializedType;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return null;
    }

    @Override
    public <T> byte[] serialize(T object) {
        return new byte[0];
    }
}
