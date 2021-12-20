package top.pofengsystem.core.serializable;


import top.pofengsystem.core.constant.SerializedTypeConstant;

public class MessageSerializerFactory {
    public static MessageSerializer getSerializer(byte messageSerializer) {
        if (SerializedTypeConstant.JDK_SERIALIZABLE == messageSerializer) {
            return new JDKSerializer();
        } else if (SerializedTypeConstant.JSON_SERIALIZABLE == messageSerializer) {
            return new JSONSerializer();
        }
        return null;
    }

    public static MessageSerializer getDefaultSerializer() {
        return new JSONSerializer();
    }
}
