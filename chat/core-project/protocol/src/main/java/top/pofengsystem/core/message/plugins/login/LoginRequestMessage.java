package top.pofengsystem.core.message.plugins.login;


import top.pofengsystem.core.annotation.MessageBean;
import top.pofengsystem.core.constant.MessageTypeConstant;
import top.pofengsystem.core.message.AbstractRequestMessage;
import top.pofengsystem.core.serializable.MessageSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录请求协议
 */
@ToString
@MessageBean
@EqualsAndHashCode(callSuper = true)
public class LoginRequestMessage extends AbstractRequestMessage {
    /**
     * 协议类型
     */
    private static final byte messageType = MessageTypeConstant.LOGIN_REQUEST_MSG;

    /**
     * 用户名
     */
    @Getter
    @Setter
    private String username;

    /**
     * 密码
     */
    @Getter
    @Setter
    private String password;

    public LoginRequestMessage(MessageSerializer messageSerializer) {
        super(messageSerializer);
    }


    @Override
    public int getMessageType() {
        return messageType;
    }

}
