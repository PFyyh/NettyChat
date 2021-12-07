package com.start.four;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

@Slf4j
public class DoubleChatServer {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buffer = (ByteBuf) msg;
                                        log.info(buffer.toString(Charset.defaultCharset()));

                                        // 建议使用 ctx.alloc() 创建 ByteBuf
                                        ByteBuf response = ctx.alloc().buffer();
                                        response.writeBytes(buffer);
                                        ctx.writeAndFlush(response);

                                    }
                                })
                                .addLast(new ChannelOutboundHandlerAdapter() {
                                    @Override
                                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                        log.info("向客户端发送消息：" + msg);
                                        super.write(ctx, msg, promise);
                                    }
                                });
                    }
                })
                .bind(9000);
    }
}
