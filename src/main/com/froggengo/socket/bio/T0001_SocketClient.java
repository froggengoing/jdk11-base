package com.froggengo.socket.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author fly
 * @create 2024-05-14-1:03
 **/
public class T0001_SocketClient {
    public static void main(String[] args) throws IOException {
        try (
                //Socket socket = new Socket("127.0.0.1", 10080);
                //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
                //Socket socket = new Socket("172.24.136.195", 10080);
                Socket socket = new Socket("127.0.0.1", 10080);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            new Thread(() -> {
                while (true) {
                    try {
                        String s = in.readLine();
                        System.out.println("read from server:" + s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            int count = 0;
            while (true) {
                //out.println("hello server:" + count++);
                out.println(count++);
                System.out.println("writer to server");
                Thread.sleep(5000);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
