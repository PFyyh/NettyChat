package com.start.six.protocol.core;

import com.start.six.protocol.core.serializable.JSONSerializer;
import com.start.six.protocol.core.serializable.MessageSerializer;

public abstract class RequestMessage extends AbstractMessage {
    /**
     * 默认使用json解析
     */
    public RequestMessage() {
        this(new JSONSerializer());
    }

    public RequestMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }
}
