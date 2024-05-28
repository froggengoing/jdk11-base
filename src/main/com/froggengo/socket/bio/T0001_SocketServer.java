package com.froggengo.socket.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author fly
 * @create 2024-05-14-0:48
 **/
public class T0001_SocketServer {
    public static void main(String[] args) throws IOException {
        /**
         * 创建socket服务器
         */
        int port = 10080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("open server socket in port:" + port);
        while (true) {
            /**
             * 监听客户端请求
             */
            Socket socket = serverSocket.accept();
            System.out.println("客户端请求");
            new Thread(() -> {
                try (
                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter out = new PrintWriter(outputStream, true);
                        InputStream inputStream = socket.getInputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))
                ) {

                    int count = 0;
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                        out.println("me too!" + count++);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
    //public static void main(String[] args) {
    //    final int portNumber = 10080; // 选择一个可用的端口号
    //
    //    try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
    //        System.out.println("等待客户端连接...");
    //
    //        while (true) {
    //            Socket clientSocket = serverSocket.accept();
    //            System.out.println("客户端已连接");
    //
    //            // 为每个客户端连接创建一个新的线程
    //            new Thread(() -> {
    //                try (
    //                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
    //                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
    //                ) {
    //                    String inputLine;
    //                    while ((inputLine = in.readLine()) != null) {
    //                        System.out.println("客户端消息: " + inputLine);
    //                        out.println("服务端收到消息: " + inputLine);
    //                    }
    //                } catch (IOException e) {
    //                    System.out.println("通信错误: " + e.getMessage());
    //                } finally {
    //                    try {
    //                        clientSocket.close();
    //                        System.out.println("客户端连接已关闭");
    //                    } catch (IOException e) {
    //                        System.out.println("关闭客户端连接时出错: " + e.getMessage());
    //                    }
    //                }
    //            }).start();
    //        }
    //    } catch (IOException e) {
    //        System.out.println("创建服务器套接字时出错: " + e.getMessage());
    //    }
    //}
}
