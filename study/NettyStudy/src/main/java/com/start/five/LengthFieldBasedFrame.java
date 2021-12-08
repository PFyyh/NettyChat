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
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LengthFieldBasedFrame {
    private static class BondingServer {
        private NioEventLoopGroup nioEventLoopGroup;

        public BondingServer() {
            nioEventLoopGroup = new NioEventLoopGroup();
            try{
                new ServerBootstrap()
                        .group(nioEventLoopGroup)
                        .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_RCVBUF, 16)
                        .childHandler(new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                                //maxFrameLength 窗口最大长度
                                //lengthFieldOffset 长度偏移 -0就是不偏移。从头就是。
                                //lengthFieldLength 长度占用字节
                                //initialBytesToStrip 舍弃字段
                                nioSocketChannel.pipeline()
                                        .addLast(new LengthFieldBasedFrameDecoder(
                                                1024, 0, 4, 0, 4))
                                        .addLast(new LoggingHandler(LogLevel.DEBUG))
                                        .addLast(new StringDecoder())
                                        .addLast(new ChannelInboundHandlerAdapter(){
                                            @Override
                                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                                log.info("msg:{}",msg);
                                            }
                                        });
                            }
                        }).bind(9000);
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                nioEventLoopGroup.shutdownGracefully();
            }
        }

        public void close() {
            nioEventLoopGroup.shutdownGracefully();
        }
    }

    private static class BondingClient {
        private NioEventLoopGroup nioEventLoopGroup;

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
                                final ByteBuf buffer = ctx.alloc().buffer();
                                send(buffer, "sdfsdfsdf");
                                send(buffer, "sdfsqweqweqweop12j3pi1j23i1p2j3dfsdf");
                                send(buffer, "jwoierjoi3jr2io3j42oi34");
                                send(buffer, "中文");
                                ctx.writeAndFlush(buffer);
                                ctx.channel().close();
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

    private static void send(ByteBuf byteBuf, String msg) {
        byteBuf.writeInt(msg.getBytes(StandardCharsets.UTF_8).length);
        log.info("长度：{},字节数组长度：{}", msg.length(), msg.getBytes(StandardCharsets.UTF_8).length);
        byteBuf.writeBytes(msg.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws InterruptedException {
        final BondingServer bondServer = new BondingServer();
        for (int i = 0; i < 10; i++) {
            final BondingClient bondingClient = new BondingClient("1234567812345678");
            bondingClient.close();
        }
        Thread.sleep(5000);
        bondServer.close();
    }

}
