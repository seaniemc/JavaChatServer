package com.ericsson.sean.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by smcgrath on 7/7/2017.
 */
public class ChatServer {
    public ChatServer (int port) throws IOException {
        ServerSocket server = new ServerSocket(port);
        while (true){
            //server accepts clients
            Socket client = server.accept();
            System.out.println("Accepted from " + client.getInetAddress());

            //Creates new instance of the ChatHandler class
            ChatHandler c = new ChatHandler(client);
            c.start();
        }
    }
    public static void main (String args[]) throws IOException {
        if(args.length != 1)
            throw new RuntimeException("Syntax: ChatServer <port>");

        new ChatServer(Integer.parseInt(args[0]));

    }
}
