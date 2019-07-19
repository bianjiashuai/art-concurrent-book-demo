package com.bjs.part04;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @Description
 * @Author BianJiashuai
 */
public class Piped {

    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter(); // 写入
        PipedReader in = new PipedReader(); // 读取

        // 将输出流和输入流连接，否则会抛出IOException异常
        out.connect(in);

        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive;
        try {
            while ((receive = System.in.read()) != -1) {
                out.write(receive);
            }
        } finally {
            out.close();
        }

    }

    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException ex) {

            }

        }
    }
}
