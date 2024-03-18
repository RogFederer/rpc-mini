package com.yhr.rpc.client;

import com.yhr.rpc.Peer;
import com.yhr.rpc.codec.Decoder;
import com.yhr.rpc.codec.Encoder;
import com.yhr.rpc.codec.JSONDecoder;
import com.yhr.rpc.codec.JSONEncoder;
import com.yhr.rpc.transport.HTTPTransportClient;
import com.yhr.rpc.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClass=
            HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass= JSONEncoder.class;
    private Class<? extends Decoder> decoderClass= JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass=
            RandomTransportSelector.class;
    private int connectCount=1;
    private List<Peer> servers= Arrays.asList(
            new Peer("127.0.0.1",3000)
    );
}
