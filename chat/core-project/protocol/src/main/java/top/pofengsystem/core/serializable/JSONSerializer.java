package top.pofengsystem.core.serializable;


import top.pofengsystem.core.constant.SerializedTypeConstant;

public class JSONSerializer implements MessageSerializer{
    @Override
    public byte getSerializedType() {
        return SerializedTypeConstant.JSON_SERIALIZABLE;
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
