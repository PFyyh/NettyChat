package top.pofengsystem.server.component;

import top.pofengsystem.core.codec.MessageCodecSharable;
import top.pofengsystem.core.codec.ProtocolFrameDecoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.logging.LoggingHandler;
import top.pofengsystem.server.component.login.LoginHandler;

public class ServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new ProtocolFrameDecoder())
                .addLast(new LoggingHandler())
                .addLast(new MessageCodecSharable())
                .addLast(new LoginHandler());
    }
}
