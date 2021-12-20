package top.pofengsystem.server.component.login;

import top.pofengsystem.core.constant.MessageTypeConstant;
import top.pofengsystem.core.constant.SerializedTypeConstant;
import top.pofengsystem.core.message.AbstractMessage;
import top.pofengsystem.core.message.MessageFactory;
import top.pofengsystem.core.message.plugins.login.LoginRequestMessage;
import top.pofengsystem.core.message.plugins.login.LoginResponseMessage;
import top.pofengsystem.core.message.plugins.server.impl.LoginBroadMessage;
import top.pofengsystem.handler.login.AbstractLoginRequestHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import top.pofengsystem.server.component.NettyConfig;

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
        log.debug("注册客户端：{}", channelHandlerContext);
        NettyConfig.group.add(message.getUsername(),channelHandlerContext.channel());
        //开始广播其他用过户
        broadcastLogin(message, channelHandlerContext.channel());
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

    private void broadcastLogin(LoginRequestMessage loginRequestMessage, Channel myself) {
        NettyConfig.group.stream()
                .filter(channel -> channel.id() != myself.id())
                .forEach(channel -> {
                    final LoginBroadMessage loginBroadMessage = (LoginBroadMessage) MessageFactory.getMessageBean(MessageTypeConstant.LOGIN_BROAD_MSG, SerializedTypeConstant.JDK_SERIALIZABLE);
                    assert loginBroadMessage != null;
                    loginBroadMessage.setContent("用户:" + loginBroadMessage.getContent() + ",已经进入聊天室~");
                    channel.writeAndFlush(loginBroadMessage);
                });

    }


}
