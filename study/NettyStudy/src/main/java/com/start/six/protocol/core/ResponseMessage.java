package com.start.six.protocol.core;

import com.start.six.protocol.core.serializable.JSONSerializer;
import com.start.six.protocol.core.serializable.MessageSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public abstract class ResponseMessage extends AbstractMessage {
    /**
     * 响应编码
     */
    protected int code;
    /**
     * 响应消息
     */
    protected String msg;

    /**
     * 默认使用json解析
     */
    public ResponseMessage() {
        this(new JSONSerializer());
    }

    public ResponseMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }
}
