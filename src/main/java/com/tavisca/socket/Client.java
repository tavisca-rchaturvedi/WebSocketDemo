package com.tavisca.socket;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String clientMessage = "", serverMessage = "";

            while (!clientMessage.equals("bye")) {
                clientMessage = br.readLine();
                outStream.writeUTF(clientMessage);
                outStream.flush();

                serverMessage = inStream.readUTF();
                System.out.println("From Server: " + serverMessage);

            }
            inStream.close();
            outStream.close();
            br.close();
        }
        catch(Exception e){

        }
    }
}
