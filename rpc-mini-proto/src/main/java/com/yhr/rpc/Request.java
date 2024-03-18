package com.yhr.rpc;

import lombok.Data;

/**
 * 表示RPC的请求
 */
@Data
public class Request {
    private ServiceDescriptor service;
    private Object[] parameters;
}
