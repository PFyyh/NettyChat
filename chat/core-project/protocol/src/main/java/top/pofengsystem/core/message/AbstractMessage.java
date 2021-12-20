package top.pofengsystem.core.message;


import top.pofengsystem.core.serializable.MessageSerializer;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j

@ToString(callSuper = true)
public abstract class AbstractMessage implements Message {
    /**
     * 分片id
     */
    protected int sequenceId;

    /**
     * 序列化器
     */
    MessageSerializer messageSerializer;

    @Override
    abstract public int getMessageType();

    public AbstractMessage(MessageSerializer messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        log.debug("开始序列化{}...", clazz);
        final T result = messageSerializer.deserialize(clazz, bytes);
        log.debug("开始序列化完成{}。", clazz);
        return result;
    }

    public <T> byte[] serialize(T object) {
        return messageSerializer.serialize(object);
    }


}
