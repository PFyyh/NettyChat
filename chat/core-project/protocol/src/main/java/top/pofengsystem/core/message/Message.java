package top.pofengsystem.core.message;

import java.io.Serializable;

public interface Message extends Serializable {
    /**
     * 魔数
     */
    byte[] MAGIC_NUMBER = "GGS,DDU!".getBytes();

    /**
     * 协议
     */
    byte[] PROTOCOL_VERSION="CHAT/0.1".getBytes();

    /**
     * 获取消息类型
     * @return 消息类型
     */
    int getMessageType();






}

