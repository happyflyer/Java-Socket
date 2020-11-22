package org.example.java_socket.socket2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用多线程实现服务端与多客户端通信
 *
 * @author lifei
 */
public class Server {
    public static void main(String[] args) {
        try {
            // 1. 创建 ServerSocket 对象，绑定监听端口
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("***服务器已经启动，开始侦听客户端的连接***");
            int count = 0;
            // 2. 通过 accept() 方法监听客户端请求
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                ServerThread thread = new ServerThread(socket);
                thread.setPriority(4);
                thread.start();
                count++;
                System.out.println("客户端的数量为：" + count);
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的地址为：" + address.getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
