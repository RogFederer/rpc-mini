package com.yhr.rpc.example;

import com.yhr.rpc.client.RpcClient;

public class Client {
    public static void main(String[] args) {
        RpcClient client=new RpcClient();
        CalculateService service=client.getProxy(CalculateService.class);

        int r1= service.add(1,2);
        int r2= service.minus(10,8);

        System.out.println(r1);
        System.out.println(r2);
    }
}
