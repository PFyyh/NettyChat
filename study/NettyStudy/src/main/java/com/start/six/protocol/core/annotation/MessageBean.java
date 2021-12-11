package com.start.six.protocol.core.annotation;

import java.lang.annotation.*;

/**
 * 用于注册MessageBean
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MessageBean {
}
