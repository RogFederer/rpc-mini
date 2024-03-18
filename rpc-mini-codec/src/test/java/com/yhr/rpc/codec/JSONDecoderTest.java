package com.yhr.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONDecoderTest {

    @Test
    public void decode() {
        Encoder encoder=new JSONEncoder();
        TestBean bean=new TestBean();
        TestBean bean2=new TestBean();
        bean2.setName("rpc");
        bean2.setAge(11);

        bean.setAge(10);
        bean.setName("yhr");

        byte[] bytes = encoder.encode(bean);

        Decoder decoder=new JSONDecoder();
        TestBean bean1=decoder.decode(bytes, TestBean.class);
        assertEquals(bean.getName(),bean1.getName());
        assertEquals(bean.getAge(),bean1.getAge());
    }
}