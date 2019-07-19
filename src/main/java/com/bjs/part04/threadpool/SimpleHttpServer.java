package com.bjs.part04.threadpool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description
 * @Author BianJiashuai
 */
public class SimpleHttpServer {
    // 线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(1);
    // 根路径
    static String basePath;
    static ServerSocket serverSocket;
    static int port;

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        if (basePath != null && new File(basePath).exists() && new File(basePath).isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    public static void main(String[] args) throws IOException {
        String basePath = "E:\\my_project";
        int port = 10101;
        setBasePath(basePath);
        setPort(port);
        start();
    }

    public static void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("WEB文件服务已启动...");
        Socket socket;
        while ((socket = serverSocket.accept()) != null) {
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {
        private Socket socket;
        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                System.out.println(header);
                // 由相对路径算出绝对路径
                String filePath = basePath + header.split(" ")[1].replace("/", "\\");
                out = new PrintWriter(socket.getOutputStream());
                // 如果请求后缀为jpg或ico, 则读物资源取出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: BJS");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: BJS");
                    out.println("Content-Type: text/html; charset=UTF-8");
                    out.println("");
                    while ((line = reader.readLine()) != null) {
                        out.println(line);
                    }
                }
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(reader, out, in, socket);
            }
        }

        private static void close(Closeable...closeables) {
            if (closeables != null) {
                for (Closeable closeable: closeables) {
                    try {
                        if (closeable != null) {
                            closeable.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
