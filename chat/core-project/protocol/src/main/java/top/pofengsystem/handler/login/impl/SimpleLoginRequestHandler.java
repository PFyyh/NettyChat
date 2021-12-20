package top.pofengsystem.handler.login.impl;

import top.pofengsystem.core.message.plugins.login.LoginRequestMessage;
import top.pofengsystem.handler.login.AbstractLoginRequestHandler;
import io.netty.channel.ChannelHandlerContext;

public class SimpleLoginRequestHandler extends AbstractLoginRequestHandler {

    @Override
    public boolean deal(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        return false;
    }

    @Override
    public void success(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
    }

    @Override
    public void fail(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
    }
}
