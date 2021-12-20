package top.pofengsystem;

import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.pofengsystem.server.component.NettyConfig;
import top.pofengsystem.server.component.ServerBootStrap;

import java.net.InetSocketAddress;

/**
 * 借用https://blog.csdn.net/xiaoping0915/article/details/81202851
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
public class ChatServerApplication implements CommandLineRunner {

    @Autowired
    private ServerBootStrap server;


    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(ChatServerApplication.class, args);
    }

    // 注意这里的 run 方法是重载自 CommandLineRunner
    @Override
    public void run(String... args) {
        log.info("Netty's ws server is listen: " + NettyConfig.WS_PORT);
        InetSocketAddress address = new InetSocketAddress(NettyConfig.WS_HOST, Integer.parseInt(NettyConfig.WS_PORT));
        ChannelFuture future = server.start(address);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.destroy()));

        future.channel().closeFuture().syncUninterruptibly();
    }

}
