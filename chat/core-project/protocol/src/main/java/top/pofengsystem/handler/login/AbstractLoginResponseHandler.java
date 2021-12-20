package top.pofengsystem.handler.login;

import top.pofengsystem.core.message.plugins.login.LoginResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractLoginResponseHandler extends SimpleChannelInboundHandler<LoginResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponseMessage loginResponseMessage) {
        try {
            deal(channelHandlerContext,loginResponseMessage);
        } catch (Exception e) {
            processException(channelHandlerContext, loginResponseMessage, e);
        }
    }

    protected abstract void deal(ChannelHandlerContext channelHandlerContext, LoginResponseMessage loginResponseMessage);

    protected void processException(ChannelHandlerContext channelHandlerContext, LoginResponseMessage loginResponseMessage, Exception e){
        e.printStackTrace();
    }
}
