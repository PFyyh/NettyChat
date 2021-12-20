package top.pofengsystem.handler.login;

import top.pofengsystem.core.constant.MessageTypeConstant;
import top.pofengsystem.core.constant.SerializedTypeConstant;
import top.pofengsystem.core.message.plugins.login.LoginResponseMessage;
import top.pofengsystem.core.message.MessageFactory;
import top.pofengsystem.core.message.plugins.login.LoginRequestMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginHandler extends AbstractLoginRequestHandler {
    @Override
    public boolean deal(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        return true;
    }

    @Override
    public void success(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        final LoginResponseMessage messageBean = (LoginResponseMessage) MessageFactory.getMessageBean(MessageTypeConstant.LOGIN_RESPONSE_MSG, SerializedTypeConstant.JDK_SERIALIZABLE);
        assert messageBean != null;
        messageBean.setCode(200);
        messageBean.setMsg("登录成功。。。");
        channelHandlerContext.writeAndFlush(messageBean);
    }

    @Override
    public void fail(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        final LoginResponseMessage messageBean = (LoginResponseMessage) MessageFactory.getMessageBean(MessageTypeConstant.LOGIN_RESPONSE_MSG, SerializedTypeConstant.JDK_SERIALIZABLE);
        assert messageBean != null;
        messageBean.setCode(400);
        messageBean.setMsg("登录失败。。。");
        channelHandlerContext.writeAndFlush(messageBean);
    }
}
