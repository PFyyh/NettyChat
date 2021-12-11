package com.start.six.protocol.core.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MessageScan {
    /**
     * 扫描包路径
     * @return  需要扫描的包
     */
    String[] value() default {};

}
