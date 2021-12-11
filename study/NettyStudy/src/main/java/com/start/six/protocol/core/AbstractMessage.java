package com.start.six.protocol.core;

import com.start.six.protocol.core.serializable.JDKSerializer;
import com.start.six.protocol.core.serializable.MessageSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractMessage extends JDKSerializer implements Message {
    /**
     * 分片id
     */
    protected int sequenceId;

    /**
     * 序列化器
     */
    MessageSerializer messageSerializer;


    public AbstractMessage(MessageSerializer messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

    @Override
    abstract public int getMessageType();

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        log.debug("开始序列化{}...", clazz);
        final T result = messageSerializer.deserialize(clazz, bytes);
        log.debug("开始序列化完成{}。", clazz);
        return result;
    }

    @Override
    public <T> byte[] serialize(T object) {
        return messageSerializer.serialize(object);
    }


}
