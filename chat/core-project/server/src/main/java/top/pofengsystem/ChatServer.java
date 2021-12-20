package top.pofengsystem;

import top.pofengsystem.core.codec.MessageCodecSharable;
import top.pofengsystem.core.codec.ProtocolFrameDecoder;
import top.pofengsystem.handler.login.LoginHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        final NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    .group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline()
                                    .addLast(new ProtocolFrameDecoder())
                                    .addLast(new LoggingHandler())
                                    .addLast(new MessageCodecSharable())
                                    .addLast(new LoginHandler());
                        }
                    }).bind(9000);
        }catch (Exception e){
            e.printStackTrace();
            eventLoopGroup.shutdownGracefully();
        }
    }
}
