package com.froggengo.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author fly
 * @create 2024-05-14-12:17
 **/
public class T0001_ServerSocketChannel {


    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        int port = 10080;
        serverSocketChannel.bind(new InetSocketAddress(port));
        // 设置非阻塞模式,否则下面的accept会阻塞
        serverSocketChannel.configureBlocking(false);
        //serverSocketChannel.accept();
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start success port:" + port);
        while (true) {
            int select = selector.select();
            System.out.println("selector.select()");
            if (select <= 0) {
                //System.out.println("selector.select() <= 0");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("selectionKey.isAcceptable()");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    System.out.println("selectionKey.isReadable()");
                    handle(selectionKey);
                }
                if (selectionKey.isWritable()) {
                    System.out.println("selectionKey.isWritable()");
                }
                if (selectionKey.isConnectable()) {
                    System.out.println("selectionKey.isConnectable()");
                }
                if (selectionKey.isValid()) {
                    System.out.println("selectionKey.isValid()");
                }
                if (selectionKey.attachment() != null) {
                    System.out.println("selectionKey.attachment() != null");
                }
                iterator.remove();
            }
        }

    }

    private static void handle(SelectionKey selectionKey) throws IOException {
        SelectableChannel channel = selectionKey.channel();
        channel.configureBlocking(false);
        if (channel instanceof SocketChannel) {
            SocketChannel socketChannel = (SocketChannel) channel;
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);
            if (read > 0) {
                byteBuffer.flip();
                String s = new String(byteBuffer.array(), 0, read);
                System.out.println("receive client:" + s);
                socketChannel.write(ByteBuffer.wrap("hello".getBytes()));
            }
        }
    }
}
