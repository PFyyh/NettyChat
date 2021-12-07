package com.start.four;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class DoubleChatClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup executors = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(executors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast(new StringEncoder())
                                .addLast(new ChannelInboundHandlerAdapter() {

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        final Channel channel = ctx.channel();
                                        for (int i = 0; i < 10; i++) {
                                            channel.writeAndFlush(new byte[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17});
                                        }
                                    }

                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buffer = (ByteBuf) msg;
                                        log.info("服务器发送消息：{}", buffer.toString(Charset.defaultCharset()));
                                        super.channelRead(ctx, msg);
                                    }
                                });
                    }
                })
                .connect(new InetSocketAddress(9000))
                .sync();
        Channel channel = channelFuture.channel();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    channel.close();
                    break;
                }
                log.info("客户端输入信息：{}", line);
                ByteBuf request = channel.alloc().buffer();
                request.writeBytes(line.getBytes(StandardCharsets.UTF_8));
                channel.writeAndFlush(line);
            }
        }).start();
        ChannelFuture closeFuture = channel.closeFuture();
        closeFuture.sync();
        log.info("开始关闭客户端...");
        executors.shutdownGracefully();
    }
}
