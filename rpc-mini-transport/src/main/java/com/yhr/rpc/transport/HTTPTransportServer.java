package com.yhr.rpc.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class HTTPTransportServer implements TransportServer{
    private RequestHandler handler;
    private Server server;
    @Override
    public void init(int port, RequestHandler handler) {
        this.handler=handler;
        this.server=new Server(port);

        //Servlet接收请求
        ServletContextHandler ctx=new ServletContextHandler();
        server.setHandler(ctx);

        ServletHolder holder=new ServletHolder(new RequestServlet());
        ctx.addServlet(holder,"/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();  //当前线程等待服务器线程终止。这确保了服务器在启动后保持运行状态，直到显式关闭
        } catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    /**
     * 定义一个内部类
     */
    class RequestServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            log.info("client connect");

            InputStream in=req.getInputStream();
            OutputStream out= resp.getOutputStream();

            if(handler != null){
                handler.onRequest(in,out);
            }
            out.flush();
        }
    }
}
