package top.pofengsystem.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import top.pofengsystem.core.message.plugins.server.BroadMessage;

@Slf4j
public class BroadMessageHandler extends SimpleChannelInboundHandler<BroadMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BroadMessage broadMessage) throws Exception {
        log.debug("服务器发来广播...");
        log.info("消息内容：{}", broadMessage);
    }
}
