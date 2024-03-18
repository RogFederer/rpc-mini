package com.yhr.rpc.server;

import com.yhr.rpc.codec.Decoder;
import com.yhr.rpc.codec.Encoder;
import com.yhr.rpc.codec.JSONDecoder;
import com.yhr.rpc.codec.JSONEncoder;
import com.yhr.rpc.transport.HTTPTransportServer;
import com.yhr.rpc.transport.TransportServer;
import lombok.Data;

/**
 * RPCServer配置
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass= HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass= JSONEncoder.class;
    private Class<? extends Decoder> decoderClass= JSONDecoder.class;
    private int port=3000;
}
