package com.yhr.rpc.server;

import com.yhr.rpc.Request;
import com.yhr.rpc.common.utils.ReflectionUtils;

/**
 * 调用具体服务
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance service, Request request){
        return ReflectionUtils.invoke(service.getTarget(),
                service.getMethod(),
                request.getParameters());
    }
}
