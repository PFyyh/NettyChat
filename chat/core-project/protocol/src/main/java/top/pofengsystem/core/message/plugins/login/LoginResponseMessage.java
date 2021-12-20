package top.pofengsystem.core.message.plugins.login;

import top.pofengsystem.core.annotation.MessageBean;
import top.pofengsystem.core.constant.MessageTypeConstant;
import top.pofengsystem.core.message.AbstractResponseMessage;
import top.pofengsystem.core.serializable.MessageSerializer;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 登录响应
 */
@ToString(callSuper = true)
@MessageBean
@EqualsAndHashCode(callSuper = true)
public class LoginResponseMessage extends AbstractResponseMessage {

    /**
     * 协议类型
     */
    private static final byte messageType = MessageTypeConstant.LOGIN_RESPONSE_MSG;


    public LoginResponseMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }


    @Override
    public int getMessageType() {
        return messageType;
    }
}
