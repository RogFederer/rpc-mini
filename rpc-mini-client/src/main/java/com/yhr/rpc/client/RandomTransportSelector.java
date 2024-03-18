package com.yhr.rpc.client;

import com.yhr.rpc.Peer;
import com.yhr.rpc.common.utils.ReflectionUtils;
import com.yhr.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelector implements TransportSelector{

    /**
     * 已经连接好的client
     */
    private List<TransportClient> clients;

    /**
     * 用ArrayList初始化clients，由于ArrayList并不是线程安全的，因此后续方法加上synchronized
     */
    public RandomTransportSelector(){
        clients=new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count=Math.max(count,1);

        for(Peer peer : peers){
            for(int i=0;i<count;i++){
                TransportClient client= ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}",peer);
        }
    }

    /**
     * 从clients池里随机选择一个返回
     * @return
     */
    @Override
    public synchronized TransportClient select() {
        int i= new Random().nextInt(clients.size());
        //获取一个连接
        return clients.get(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients){
            client.close();
        }
        clients.clear();
    }
}
