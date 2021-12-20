package top.pofengsystem.core.message;


import top.pofengsystem.core.serializable.MessageSerializer;

public abstract class AbstractRequestMessage extends AbstractMessage {

    public AbstractRequestMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }
}
