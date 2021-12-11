package com.start.six.protocol.plugins.login;

import com.start.six.protocol.core.RequestMessage;
import com.start.six.protocol.core.annotation.MessageBean;
import com.start.six.protocol.core.constant.MessageConstant;
import lombok.*;

/**
 * 登录请求协议
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MessageBean
public class LoginRequestMessage extends RequestMessage {
    /**
     * 协议类型
     */
    private static final byte messageType = MessageConstant.LOGIN_REQUEST_MSG;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    @Override
    public int getMessageType() {
        return messageType;
    }
}
