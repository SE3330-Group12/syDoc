package com.group12.syDocbackend.websocket;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.group12.syDocbackend.entity.DocLog;
import com.group12.syDocbackend.repository.DocLogRepository;
import com.group12.syDocbackend.utils.HttpUtils;
import com.group12.syDocbackend.utils.PakoGzipUtils;
import com.group12.syDocbackend.utils.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MyWebsocketServer extends org.java_websocket.server.WebSocketServer {

    Map<WebSocket,String> connMap = new ConcurrentHashMap<>();
    Map<WebSocket,String> usernameMap = new ConcurrentHashMap<>();
    Map<WebSocket,String> docTypeMap = new ConcurrentHashMap<>();



    @Autowired
    private DocLogRepository docLogRepository;

    public MyWebsocketServer(int port){
        super(new InetSocketAddress(port));
    }

    int n = 0;
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        String resource = conn.getResourceDescriptor();
        System.out.println(resource);
        String docId = HttpUtils.getParameter(resource, "docId");
        String username=HttpUtils.getParameter(resource, "username");
        String docType=HttpUtils.getParameter(resource, "docType");
        System.out.println(username);
        System.out.println(docType);
        if(docId == null) docId = "" + n++;
        connMap.put(conn,docId);
        usernameMap.put(conn,username);
        docTypeMap.put(conn,docType);

        System.out.println(docId+" 连接 Websocket, 总连接数 = "+connMap.size());
        if(docType.equals("doc")){
            String initDoc="";
            for (DocLog docLogEntry : docLogRepository.findByDocId(docId)) {
                initDoc+=(docLogEntry.getLogEntry()+'\n');
            }
            conn.send(initDoc);
        } else if (docType.equals("sheet")) {
            for (DocLog docLogEntry : docLogRepository.findByDocId(docId)) {
                conn.send(docLogEntry.getLogEntry());
            }
        }


    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        String name = connMap.remove(conn);
        System.out.println(name+" 断开 websocket");
        docTypeMap.remove(conn);
        usernameMap.remove(conn);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        String docId=connMap.get(conn);
        String docType=docTypeMap.get(conn);
        String username=usernameMap.get(conn);
        if(docType.equals("doc")){
            System.out.println(username+" send to doc: "+docId+"message:"+message);
            connMap.forEach((socket,n) -> {
                if(conn == socket||!n.equals(docId)) return;
                socket.send(message);
            });
            docLogRepository.save(new DocLog(docId, message));
        } else if (docType.equals("sheet")) {
            try {
                if ("rub".equals(message)) {
                    return;
                }
                String unMessage= PakoGzipUtils.unCompressURI(message);
                System.out.println(username+" send to doc: "+docId+"message:"+unMessage);
                JSONObject jsonObject= JSON.parseObject(unMessage);
                connMap.forEach((socket,n)->{
                    if(conn == socket||!n.equals(docId)) return;
                    if ("mv".equals(jsonObject.getString("t"))) {
                        socket.send(JSON.toJSONString(new ResponseDTO(3, username, username, unMessage)));
                    }else if(!"shs".equals(jsonObject.getString("t"))){
                        socket.send(JSON.toJSONString(new ResponseDTO(2, username, username, unMessage)));
                        docLogRepository.save(new DocLog(docId, JSON.toJSONString(new ResponseDTO(2, username, username, unMessage))));
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("wrong doc type");
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("ws start http://localhost:"+getAddress().getPort());
    }
}

