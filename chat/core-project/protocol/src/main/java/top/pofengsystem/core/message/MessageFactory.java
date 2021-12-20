package top.pofengsystem.core.message;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import top.pofengsystem.core.annotation.MessageBean;
import top.pofengsystem.core.annotation.MessageScan;
import top.pofengsystem.core.exception.MessageBeanTypeConflictException;
import top.pofengsystem.core.serializable.MessageSerializer;
import top.pofengsystem.core.serializable.MessageSerializerFactory;
import top.pofengsystem.core.util.ClassUtil;
import top.pofengsystem.core.util.PackageUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@MessageScan("top.pofengsystem.core.message.plugins")
public class MessageFactory {


    private static final Map<Byte, Class<AbstractMessage>> messageMap = new HashMap<>();

    /**
     * 消息全限定名称
     */
    private static final List<String> messageNames = new ArrayList<>();

    static {
        //获取所有消息配置类
        final MessageScan messageScan = MessageFactory.class.getAnnotation(MessageScan.class);
        final String[] packages = messageScan.value();
        HashSet<String> packageSet = new HashSet<>();
        CollectionUtil.addAll(packageSet, packages);
        try {
            final List<Class<?>> configuration = PackageUtil.loadClassByLoader(MessageFactory.class.getClassLoader(), MessageScan.class);
            for (Class<?> tmp : configuration) {
                final String[] value = tmp.getAnnotation(MessageScan.class).value();
                CollectionUtil.addAll(packageSet, value);
                log.debug("扫描包：{}", packageSet);
                scan(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        PackageUtil.getClassName()

    }

    /**
     * 根据消息类型，和序列化方式，获取消息对象
     * @param messageType 类型
     * @param messageSerializer 序列化方式
     * @return 对象
     */
    public static AbstractMessage getMessageBean(byte messageType, Byte messageSerializer) {
        Assert.isTrue(messageMap.containsKey(messageType), "类型错误");
        final Class<AbstractMessage> messageClass = messageMap.get(messageType);
        try {
            final Constructor<AbstractMessage> constructor = messageClass.getConstructor(MessageSerializer.class);
            MessageSerializer serializer;
            //如果有指定序列化方式，设置序列化方式
            if (messageSerializer!=null){
                serializer = MessageSerializerFactory.getSerializer(messageSerializer);
            }else {
                //否则设置默认的方式
                serializer = MessageSerializerFactory.getDefaultSerializer();
            }
            return constructor.newInstance(serializer);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void register(Class<AbstractMessage> message) {
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
        log.debug("信息类型：{}", type);
    }

    @SuppressWarnings("unchecked")
    public static void scan(String[] packages) {
        for (String basePackage : packages) {
            final List<String> classNames = PackageUtil.getClassName(basePackage);
            for (String className : classNames) {
                try {
                    final Class<?> tmp = Class.forName(className);
                    if (ClassUtil.containParentClass(tmp, AbstractMessage.class)) {
                        register((Class<AbstractMessage>) tmp);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
