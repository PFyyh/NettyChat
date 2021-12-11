package com.start.six;

import com.start.six.protocol.core.annotation.AnnotationMessageApplicationContext;
import com.start.six.protocol.core.annotation.MessageScan;
import com.start.six.protocol.core.constant.MessageConstant;
import com.start.six.protocol.plugins.login.LoginRequestMessage;


@MessageScan("com.start.six")
public class Test {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        final AnnotationMessageApplicationContext messageApplicationContext = new AnnotationMessageApplicationContext(Test.class);
    }
}
