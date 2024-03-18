package com.yhr.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder=new JSONEncoder();
        TestBean bean=new TestBean();
        TestBean bean1=new TestBean();

        bean.setAge(10);
        bean.setName("yhr");

        byte[] bytes = encoder.encode(bean);



        assertNotNull(bytes);
    }
}