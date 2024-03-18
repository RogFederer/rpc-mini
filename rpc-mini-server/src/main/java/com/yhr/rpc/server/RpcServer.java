package com.yhr.rpc.server;

import com.yhr.rpc.Request;
import com.yhr.rpc.Response;
import com.yhr.rpc.codec.Decoder;
import com.yhr.rpc.codec.Encoder;
import com.yhr.rpc.common.utils.ReflectionUtils;
import com.yhr.rpc.transport.RequestHandler;
import com.yhr.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(){
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config){
        this.config=config;
        //net
        this.net= ReflectionUtils.newInstance(config
                .getTransportClass());
        this.net.init(config.getPort(),this.handler);

        //codec
        this.encoder=ReflectionUtils.newInstance(config
                .getEncoderClass());
        this.decoder=ReflectionUtils.newInstance(config
                .getDecoderClass());

        //service
        this.serviceManager=new ServiceManager();
        this.serviceInvoker=new ServiceInvoker();

    }

    public <T> void register(Class<T> interfaceClass, T bean){
        serviceManager.register(interfaceClass,bean);
    }
    public void start(){
        this.net.start();
    }
    public void stop(){
        this.net.stop();
    }
    private RequestHandler handler=new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {
            Response resp=new Response();

            try {
                byte[] inBytes= IOUtils.readFully(receive,receive.available());
                Request request=decoder.decode(inBytes, Request.class);
                log.info("Get Request : {}",request);

                ServiceInstance sis=serviceManager.lookup(request);
                Object ret=serviceInvoker.invoke(sis,request);
                resp.setData(ret);
            } catch (Exception e) {
                //捕获异常的处理，将resp的返回码置为1
                log.warn(e.getMessage(),e);
                resp.setCode(1);
                resp.setMessage("RPCServer got error : "
                + e.getClass().getName()
                + " : "
                +e.getMessage());
            } finally {
                try {
                    byte[] outBytes= encoder.encode(resp);
                    toResp.write(outBytes);

                    log.info("Response client");
                } catch (IOException e){
                    log.warn(e.getMessage(),e);
                }
            }

        }
    };
}
