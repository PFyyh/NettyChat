package com.start.three;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class ByteBufTest {
    public static void main(String[] args) {
        final ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
        buffer.writeBytes(new byte[]{1,2,3,4});
    }
}
