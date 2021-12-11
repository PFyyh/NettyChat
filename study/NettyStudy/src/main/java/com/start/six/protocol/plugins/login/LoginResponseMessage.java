package com.start.six.protocol.plugins.login;

import com.start.six.protocol.core.ResponseMessage;
import com.start.six.protocol.core.annotation.MessageBean;
import com.start.six.protocol.core.annotation.MessageScan;
import com.start.six.protocol.core.constant.MessageConstant;

@MessageBean
public class LoginResponseMessage extends ResponseMessage {
    /**
     * 协议类型
     */
    private static final byte messageType = MessageConstant.LOGIN_RESPONSE_MSG;

}
