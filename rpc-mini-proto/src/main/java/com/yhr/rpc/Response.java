package com.yhr.rpc;

import lombok.Data;

/**
 * 表示RPC的响应
 */
@Data
public class Response {
    /**
     * 服务返回值 0-成功 非0-失败
     */
    private int code=0;
    /**
     * 服务返回信息
     */
    private String message="ok";
    /**
     * 服务返回的数据
     */
    private Object data;
}
