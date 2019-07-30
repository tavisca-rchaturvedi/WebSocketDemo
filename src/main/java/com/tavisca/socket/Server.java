package com.tavisca.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket server = new ServerSocket(8888);
            Socket serverClient = server.accept();

            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "", serverMessage = "";

            while(!clientMessage.equals("bye")){
                clientMessage = inStream.readUTF();
                System.out.println("From Client: " + clientMessage);

                serverMessage = reader.readLine();
                outStream.flush();
                outStream.writeUTF(serverMessage);

            }
            inStream.close();
            outStream.close();
            reader.close();
        }
        catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }
}
