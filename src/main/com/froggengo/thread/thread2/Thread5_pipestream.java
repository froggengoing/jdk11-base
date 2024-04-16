package com.froggengo.thread.thread2;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Thread5_pipestream {
    public static void main(String[] args) throws IOException {
        try (PipedReader reader = new PipedReader();
             PipedWriter writer = new PipedWriter()) {
            writer.connect(reader);
            new Thread(() -> {
//                子线程负责打印
                int r;
                try {
                    while ((r = reader.read()) != -1) {
                        System.out.print(((char) r));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
//            主线程负责收集控制台输入
            int c;
            while ((c = System.in.read()) != -1) {
                writer.write(c);
            }

        }

    }
}
