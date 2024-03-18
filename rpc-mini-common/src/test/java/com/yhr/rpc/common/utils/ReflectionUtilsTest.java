package com.yhr.rpc.common.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass t=ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(t);
    }

    @Test
    public void getPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        int length = methods.length;
        assertEquals(1,length);

        String name = methods[0].getName();
        assertEquals("b",name);
    }

    @Test
    public void invoke() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method b=methods[0];

        TestClass t=new TestClass();
        Object invoke = ReflectionUtils.invoke(t, b);
        assertEquals("b",invoke);
    }
}