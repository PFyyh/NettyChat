package top.pofengsystem.core.codec;


import top.pofengsystem.core.message.AbstractMessage;
import top.pofengsystem.core.message.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, AbstractMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractMessage abstractMessage, List<Object> list) throws Exception {
        final ByteBuf buffer = channelHandlerContext.alloc().buffer();
        //魔数
        buffer.writeBytes(AbstractMessage.MAGIC_NUMBER);
        log.debug("魔数字节长度：{}", AbstractMessage.MAGIC_NUMBER.length);
        //版本
        buffer.writeBytes(AbstractMessage.PROTOCOL_VERSION);
        log.debug("协议字节长度：{}", AbstractMessage.PROTOCOL_VERSION.length);
        //序列化方式
        buffer.writeByte(abstractMessage.getMessageSerializer().getSerializedType());
        log.debug("序列化类型：{}", abstractMessage.getMessageSerializer().getSerializedType());
        //获取指令
        buffer.writeByte(abstractMessage.getMessageType());
        log.debug("指令类别:{}", abstractMessage.getMessageType());
        //获取分片
        buffer.writeInt(abstractMessage.getSequenceId());
        log.debug("分片字节：{}", abstractMessage.getSequenceId());
        //获取长度
        final byte[] bytes = abstractMessage.serialize(abstractMessage);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        list.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buffer, List<Object> list) throws Exception {
        //魔数 8个字节
        byte[] magicNum = new byte[AbstractMessage.MAGIC_NUMBER.length];
        buffer.readBytes(magicNum, 0, AbstractMessage.MAGIC_NUMBER.length);
        log.debug("解析魔数：" + new String(magicNum));
        //版本 8个字节
        final byte[] protocolVersion = new byte[AbstractMessage.PROTOCOL_VERSION.length];
        buffer.readBytes(protocolVersion, 0, AbstractMessage.PROTOCOL_VERSION.length);
        log.debug("协议字节长度：{}", new String(protocolVersion));
        //序列化方式 1个字节
        byte serializedType = buffer.readByte();
        log.debug("序列化方式：{}",serializedType);
        //获取指令 1个字节
        byte messageType = buffer.readByte();
        log.debug("指令类别:{}", messageType);
        final AbstractMessage messageBean = MessageFactory.getMessageBean(messageType, serializedType);
        //获取分片 4个字节
        int sequenceId = buffer.readInt();
        log.debug("分片字节：{}", sequenceId);
        //获取长度
        int length = buffer.readInt();
        log.debug("长度：{}", length);
        byte[] content = new byte[length];
        buffer.readBytes(content,0,length);
        assert messageBean != null;
        final AbstractMessage message = messageBean.deserialize(messageBean.getClass(), content);
        log.debug("{}",message.toString());

        list.add(message);
    }
}
