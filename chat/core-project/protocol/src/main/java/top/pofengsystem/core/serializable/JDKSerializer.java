package top.pofengsystem.core.serializable;

import top.pofengsystem.core.constant.SerializedTypeConstant;

import java.io.*;

public class JDKSerializer implements MessageSerializer {

    private static final long serialVersionUID = 1L;
    private static final byte serializedType = SerializedTypeConstant.JDK_SERIALIZABLE;

    @Override
    public byte getSerializedType() {
        return serializedType;
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("反序列化失败", e);
        }
    }

    @Override
    public <T> byte[] serialize(T object) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("序列化失败", e);
        }
    }
}
