package top.pofengsystem.core.message.plugins.server.impl;

import lombok.Getter;
import lombok.Setter;
import top.pofengsystem.core.annotation.MessageBean;
import top.pofengsystem.core.constant.BroadMessageTypeConstant;
import top.pofengsystem.core.constant.MessageTypeConstant;
import top.pofengsystem.core.message.plugins.server.BroadMessage;
import top.pofengsystem.core.serializable.MessageSerializer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@MessageBean
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LoginBroadMessage extends BroadMessage {
    /**
     * 协议类型
     */
    private static final byte messageType = MessageTypeConstant.LOGIN_BROAD_MSG;

    @Getter
    @Setter
    private String content;

    public LoginBroadMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }

    @Override
    public int getMessageType() {
        return messageType;
    }

    @Override
    public byte getBroadType() {
        return BroadMessageTypeConstant.LOGIN;
    }
}
