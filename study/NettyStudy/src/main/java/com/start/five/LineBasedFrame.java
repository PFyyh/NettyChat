package com.start.five;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class LineBasedFrame {
    private static class BondingServer {
        private final NioEventLoopGroup nioEventLoopGroup;

        public BondingServer() {
            nioEventLoopGroup = new NioEventLoopGroup();
            new ServerBootstrap()
                    .group(nioEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_RCVBUF, 16)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new LoggingHandler(LogLevel.DEBUG));
                        }
                    }).bind(9000);
        }

        public void close() {
            nioEventLoopGroup.shutdownGracefully();
        }
    }

    private static class BondingClient {
        private final NioEventLoopGroup nioEventLoopGroup;

        public BondingClient(String msg) {
            nioEventLoopGroup = new NioEventLoopGroup();
            try {
                final ChannelFuture channelFuture = new Bootstrap()
                        .group(nioEventLoopGroup)
                        //                    .option(ChannelOption.SO_SNDBUF, 16)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) {
                                final ByteBuf buffer = ctx.alloc().buffer(16);
                                buffer.writeBytes(msg.getBytes(StandardCharsets.UTF_8));
                                ctx.writeAndFlush(buffer);
//                                ctx.channel().close();
                            }
                        }).connect(new InetSocketAddress(9000)).sync();
                channelFuture.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
                nioEventLoopGroup.shutdownGracefully();
            }

        }

        public void close() {
            nioEventLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        final BondingServer bondServer = new BondingServer();
        for (int i = 0; i < 10; i++) {
            final BondingClient bondingClient = new BondingClient("1q2w3e4r1q2w\n3e4r1q2w3e4r1q2w3e4\nr1q2w3e4r1q2w3e4r1q2w3e4r\n");
            bondingClient.close();
        }
        Thread.sleep(5000);
        bondServer.close();
    }
}
