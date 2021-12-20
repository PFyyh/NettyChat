package top.pofengsystem.handler.login.impl;

import top.pofengsystem.core.message.plugins.login.LoginResponseMessage;
import top.pofengsystem.handler.login.AbstractLoginResponseHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleLoginResponseHandler extends AbstractLoginResponseHandler {
    @Override
    protected void deal(ChannelHandlerContext channelHandlerContext, LoginResponseMessage loginResponseMessage) {
        log.info("loginResponseMessage:"+loginResponseMessage);
        channelHandlerContext.writeAndFlush(loginResponseMessage);
    }

}
