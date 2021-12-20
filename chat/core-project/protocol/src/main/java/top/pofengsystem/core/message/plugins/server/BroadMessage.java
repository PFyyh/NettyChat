package top.pofengsystem.core.message.plugins.server;

import top.pofengsystem.core.message.AbstractMessage;
import top.pofengsystem.core.serializable.MessageSerializer;

/**
 * 广播消息
 */
public abstract class BroadMessage extends AbstractMessage {

    public BroadMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }

    public abstract byte getBroadType();
}
