package com.yhr.rpc.server;

import com.yhr.rpc.Request;
import com.yhr.rpc.ServiceDescriptor;
import com.yhr.rpc.common.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServiceManagerTest {

    ServiceManager serviceManager;
    @Before
    public void init(){
        serviceManager=new ServiceManager();
        TestInterface bean=new TestClass();
        serviceManager.register(TestInterface.class,bean);
    }
    @Test
    public void register() {
        TestInterface bean=new TestClass();
        serviceManager.register(TestInterface.class,bean);
    }

    @Test
    public void lookup() {
        Method method= ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor sdp=ServiceDescriptor.from(TestInterface.class,method);

        Request request=new Request();
        request.setService(sdp);
        ServiceInstance sis=serviceManager.lookup(request);

        assertNotNull(sis);
    }
}