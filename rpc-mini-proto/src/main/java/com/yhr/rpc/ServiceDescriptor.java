package com.yhr.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 用来描述服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    public static ServiceDescriptor from(Class clazz, Method method){
        ServiceDescriptor sdp=new ServiceDescriptor();
        sdp.setClazz(clazz.getName());
        sdp.setMethod(method.getName());
        sdp.setReturnType(method.getReturnType().getName());

        Class[] types = method.getParameterTypes();
        String[] strTypes=new String[types.length];
        for(int i=0;i<strTypes.length;i++){
            strTypes[i]=types[i].getName();
        }
        sdp.setParameterTypes(strTypes);

        return sdp;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ServiceDescriptor that = (ServiceDescriptor) object;
        return this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {

        return toString().hashCode();
    }

    @Override
    public String toString() {
        return "clazz = "+clazz
                +", method = "+method
                +", returnType = "+returnType
                +", parameterType = "+Arrays.toString(parameterTypes);
    }
}
