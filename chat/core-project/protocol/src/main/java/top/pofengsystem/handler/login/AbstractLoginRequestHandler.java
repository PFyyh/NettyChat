package top.pofengsystem.handler.login;

import top.pofengsystem.core.message.plugins.login.LoginRequestMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractLoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    /**
     * 处理
     *
     * @param channelHandlerContext 上下文
     * @param message               登录请求
     */
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        log.info(message.toString());
        try {
            boolean login = deal(channelHandlerContext, message);
            login = dealPostProcess(login, channelHandlerContext, message);
            if (login) {
                log.debug("登录成功。。。");
                success(channelHandlerContext, message);
                successPostProcess(channelHandlerContext, message);
            } else {
                fail(channelHandlerContext, message);
                failPostProcess(channelHandlerContext, message);
            }
        } catch (Exception e) {
            fail(channelHandlerContext, message);
        }
    }

    /**
     * 如果需要修改结果，可以调用该接口
     *
     * @param login                 登录结果
     * @param channelHandlerContext 通道上下文
     * @param message               登录请求内容
     * @return 修改结果
     */
    protected boolean dealPostProcess(boolean login, ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        log.debug("登录业务处理完成，结果{}", login);
        return login;
    }

    protected void failPostProcess(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        log.debug("登录失败，登陆失败处理器调用...");
    }

    /**
     * 成功后置处理器
     *
     * @param channelHandlerContext 内容
     * @param message               消息
     */
    protected void successPostProcess(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message) {
        log.debug("成功后置处理器调用...");
    }

    /**
     * 处理
     *
     * @param channelHandlerContext 上下文
     * @param message               登录请求
     * @return 操作结果
     */
    public abstract boolean deal(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message);

    /**
     * 成功处理
     *
     * @param channelHandlerContext 上下文
     * @param message               登录消息
     */
    public abstract void success(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message);

    /**
     * 失败处理
     *
     * @param channelHandlerContext 上下文
     * @param message               登录消息
     */
    public abstract void fail(ChannelHandlerContext channelHandlerContext, LoginRequestMessage message);


}
