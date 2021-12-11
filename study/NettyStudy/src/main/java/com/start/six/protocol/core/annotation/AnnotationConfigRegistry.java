package com.start.six.protocol.core.annotation;

import com.start.six.protocol.core.Message;

import java.util.List;

public interface AnnotationConfigRegistry {

    void register(Class<Message> message);

    void scan(List<Class<?>> configuration);
}
