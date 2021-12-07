package com.start.one;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloSever {
    public static void main(String[] args) {
        //创建启动器
        new ServerBootstrap()
                //事件轮询，accept、read、write事件处理,类似Selector
                .group(new NioEventLoopGroup())
                //ServerSocketChannel实现
                .channel(NioServerSocketChannel.class)
                //这里决定了能处理什么事件
                .childHandler(
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) {
                                nioSocketChannel.pipeline()
                                        //字符串解码器
                                        .addLast(new StringDecoder())
                                        //自定义读处理
                                        .addLast(new ChannelInboundHandlerAdapter() {
                                            @Override
                                            public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
                                                log.info(msg.toString());
                                            }
                                        });
                            }
                        }
                ).bind(9000);
    }
}
