package com.tavisca.chat;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

// Resource part of the URL
@ServerEndpoint("/endpoint")


public class ChatWebSocket {

    private ChatHelper chatHelper;
    public Thread t1;

    public ChatWebSocket(){
        this.chatHelper = new ChatHelper();

    }

    @OnOpen
    public void onOpen(Session session){
        System.out.println("Open connection: " + session.getId());

        try {
            session.getBasicRemote().sendText("Your Session Id is: " + session.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }

        t1 = new Thread(this.chatHelper,session.getId());
        t1.start();

        ChatHelper.sessionMap.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session){
        System.out.println("Closing connection: " + session.getId());
        try {
            session.getBasicRemote().sendText("Your Session with SessionId " + session.getId() + " is closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            t1.interrupt();
        }

        ChatHelper.sessionMap.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session)  {
        System.out.println("Recieved message from: " + session.getId() + " Message is " + message );
            chatHelper.sendMessage(message, session.getId());
    }

    @OnError
    public void onError(Session session, Throwable t){
        System.out.println("An Error occured");
        System.out.println(t.getStackTrace());
    }

}
