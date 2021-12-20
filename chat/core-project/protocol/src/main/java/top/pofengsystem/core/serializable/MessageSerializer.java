package top.pofengsystem.core.serializable;

import java.io.Serializable;

public interface MessageSerializer extends Serializable {


    /**
     * 获取序列化类型
     * @return 序列化类型
     */
    byte getSerializedType();

    /**
     * 反序列化方法
     * @param clazz 类对象
     * @param bytes 需要反序列化的字符串
     * @param <T> 类型
     * @return 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    /**
     * 序列化方法
     * @param object 需要序列化的对象
     * @param <T> 类型
     * @return 序列化后的字节数组
     */
    <T> byte[] serialize(T object);



}
