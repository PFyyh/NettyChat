package com.start.six.protocol.core.annotation;

import com.start.six.protocol.core.AbstractMessage;
import com.start.six.protocol.core.Message;
import com.start.six.protocol.core.exception.MessageBeanTypeConflictException;
import com.start.six.protocol.util.ClassUtil;
import com.start.six.protocol.util.PackageUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 消息应用上下文
 */
@Slf4j
public class AnnotationMessageApplicationContext implements AnnotationConfigRegistry {
    /**
     * 消息编码和消息类
     */
    Map<Byte, Class<Message>> messageMap = new HashMap<>();

    /**
     * 消息全限定名称
     */
    List<String> messageNames = new ArrayList<>();

    public AnnotationMessageApplicationContext(Class<?> basePackage) {
        final ArrayList<Class<?>> list = new ArrayList<>();
        list.add(basePackage);
        scan(list);
    }

    public AnnotationMessageApplicationContext(List<Class<?>> basePackages) {
        scan(basePackages);
    }

    /**
     * 根据消息类型字节，获得对应的消息 class
     *
     * @param messageType 消息类型
     * @return 具体的类型
     */
    public Class<?> getSpecificMessageBean(byte messageType) {
        try {
            return Class.forName(String.valueOf(messageMap.get(messageType)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public final void register(Class<Message> message) {
        if (message == null) {
            return;
        }
        final MessageBean messageBean = message.getAnnotation(MessageBean.class);
        if (messageBean == null) {
            return;
        }
        Byte type = null;
        try {
            final Field messageType = message.getDeclaredField("messageType");
            messageType.setAccessible(true);
            type = (Byte) messageType.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //如果存在就跳过注册
        if (messageNames.contains(message.getSimpleName())) {
            return;
        }
        messageNames.add(message.getName());
        log.debug("注册消息：{}", message.getName());
        //获取检查是否存在同byte的message
        if (messageMap.containsKey(type)) {
            log.error("已存在消息类型：{},将要注册消息类型：{}", messageMap.get(type), message.getName());
            throw new MessageBeanTypeConflictException("消息类型冲突");
        }
        messageMap.put(type, message);
        log.info("信息类型：{}", type);
    }

    @Override
    public void scan(List<Class<?>> configurations) {
        List<String> packages = null;
        for (Class<?> configuration : configurations) {
            final MessageScan messageScan = configuration.getAnnotation(MessageScan.class);
            if (messageScan == null) {
                log.error("配置类错误");
            }
            assert messageScan != null;
            packages = Arrays.asList(messageScan.value());

        }
        assert packages != null;
        for (String basePackage : packages) {
            final List<String> classNames = PackageUtil.getClassName(basePackage);
            for (String className : classNames) {
                try {
                    final Class<?> tmp = Class.forName(className);
                    if (ClassUtil.containParentClass(tmp, AbstractMessage.class)) {
                        register((Class<Message>) tmp);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
