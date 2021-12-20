package top.pofengsystem.core.message;

import top.pofengsystem.core.serializable.MessageSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper = true)
public abstract class AbstractResponseMessage extends AbstractMessage {
    /**
     * 响应编码
     */
    @Getter
    @Setter
    protected int code;
    /**
     * 响应消息
     */
    @Getter
    @Setter
    protected String msg;

    public AbstractResponseMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }
}
