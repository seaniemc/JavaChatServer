package com.ericsson.sean.server;

import java.io.*;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by smcgrath on 7/7/2017.
 */
public class ChatHandler extends Thread{

    protected Socket sock;
    protected DataInputStream in;
    protected DataOutputStream out;

    public ChatHandler(Socket sock) throws IOException {
        this.sock = sock;
        in = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
        out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));
    }

    //keeps a list of all the current handlers
    protected static Vector handlers = new Vector();

    public void run(){
        try{
            handlers.addElement(this);
            while (true){
                String msg = in.readUTF();
                broadcast(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            handlers.removeElement(this);
            try{
                sock.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static void broadcast (String message) {
        synchronized (handlers) {
            Enumeration e = handlers.elements ();
            while (e.hasMoreElements ()) {
                ChatHandler c = (ChatHandler) e.nextElement ();
                try {
                    synchronized (c.out) {
                        c.out.writeUTF (message);
                    }
                    c.out.flush ();
                } catch (IOException ex) {
                    try {
                        c.join();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

}


