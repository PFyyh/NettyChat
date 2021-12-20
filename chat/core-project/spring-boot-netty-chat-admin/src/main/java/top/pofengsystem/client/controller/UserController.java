package top.pofengsystem.client.controller;

import cn.hutool.Hutool;
import cn.hutool.core.net.Ipv4Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.pofengsystem.client.handler.LoginResponseHandler;
import top.pofengsystem.client.param.LoginParam;
import top.pofengsystem.core.codec.MessageCodecSharable;
import top.pofengsystem.core.codec.ProtocolFrameDecoder;
import top.pofengsystem.core.constant.MessageTypeConstant;
import top.pofengsystem.core.constant.SerializedTypeConstant;
import top.pofengsystem.core.message.MessageFactory;
import top.pofengsystem.core.message.plugins.login.LoginRequestMessage;
import top.pofengsystem.util.IPUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

@Slf4j
@RestController("/User")
public class UserController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestBody LoginParam loginParam) throws InterruptedException {
        final String ipAddr = IPUtils.getIpAddr(request);
        createChannel(loginParam);
        return ipAddr;
    }


    private void createChannel( LoginParam loginParam) throws InterruptedException {
        //第一步检查用户是否已经登录
        redisTemplate.opsForHash().putIfAbsent("online-user:"+loginParam.getUsername(),);
        final LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        final MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        new Bootstrap().group(nioEventLoopGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) {
                nioSocketChannel.pipeline()
                        .addLast(new ProtocolFrameDecoder())
                        .addLast(LOGGING_HANDLER)
                        .addLast(MESSAGE_CODEC)
                        .addLast("hello world", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                final LoginRequestMessage message = (LoginRequestMessage) MessageFactory.getMessageBean(MessageTypeConstant.LOGIN_REQUEST_MSG, SerializedTypeConstant.JDK_SERIALIZABLE);
                                assert message != null;
                                message.setUsername(loginParam.getUsername());
                                message.setPassword(loginParam.getPassword());
                                message.setSequenceId(1);
                                ctx.writeAndFlush(message);
                                super.channelActive(ctx);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("{}", msg.getClass());
                                super.channelRead(ctx, msg);
                            }
                        })
                        .addLast(new LoginResponseHandler());
            }
        }).connect(new InetSocketAddress("127.0.0.1", 9000)).sync();
    }
}
