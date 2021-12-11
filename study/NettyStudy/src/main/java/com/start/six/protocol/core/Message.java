package com.start.six.protocol.core;

import lombok.Data;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public interface Message extends Serializable {
    /**
     * 魔数
     */
    byte[] MAGIC_NUMBER = "GGST,DDUP!".getBytes();

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

