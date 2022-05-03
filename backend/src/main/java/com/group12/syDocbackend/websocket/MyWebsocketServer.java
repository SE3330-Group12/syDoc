package com.group12.syDocbackend.websocket;


import com.group12.syDocbackend.entity.DocLog;
import com.group12.syDocbackend.repository.DocLogRepository;
import com.group12.syDocbackend.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyWebsocketServer extends org.java_websocket.server.WebSocketServer {

    Map<WebSocket,String> connMap = new ConcurrentHashMap<>();

    @Autowired
    private DocLogRepository docLogRepository;

    public MyWebsocketServer(int port){
        super(new InetSocketAddress(port));
    }

    int n = 0;
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        String resource = conn.getResourceDescriptor();
        String name = HttpUtils.getParameter(resource, "name");
        if(name == null) name = "" + n++;
        connMap.put(conn,name);
        System.out.println(name+" 连接 Websocket, 总连接数 = "+connMap.size());
        String initDoc="";
        for (DocLog docLogEntry : docLogRepository.findByDocId(name)) {
            initDoc+=(docLogEntry.getLogEntry()+'\n');
        }
        conn.send(initDoc);

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String name = connMap.remove(conn);
        System.out.println(name+" 断开 websocket");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String docid=connMap.get(conn);
        System.out.println(docid+" send: "+message);
        connMap.forEach((socket,n) -> {
            if(conn == socket||!n.equals(docid)) return;
            socket.send(message);
        });
        docLogRepository.save(new DocLog(docid, message));
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("ws start http://localhost:"+getAddress().getPort());
    }
}

