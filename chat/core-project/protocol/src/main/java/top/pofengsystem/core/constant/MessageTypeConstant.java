package top.pofengsystem.core.constant;

/**
 * 指令集合
 */
public interface MessageTypeConstant {
    /**
     * 登录请求
     */
    byte LOGIN_REQUEST_MSG = 0;
    /**
     * 登录响应
     */
    byte LOGIN_RESPONSE_MSG = 1;

    /**
     * 登录广播消息
     */
    byte LOGIN_BROAD_MSG = 2;
}
