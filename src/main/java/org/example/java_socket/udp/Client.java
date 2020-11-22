package org.example.java_socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 基于 UDP 的 Socket 通信
 *
 * @author lifei
 */
public class Client {
    public static void main(String[] args) throws IOException {
        // 1. 定义发送信息
        InetAddress address = InetAddress.getLocalHost();
        int port = 8888;
        byte[] data = "user:zhangsan;passwd:888888".getBytes();
        // 2. 创建 DatagramPacket，包含所要发送的信息
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        // 3. 创建 DatagramSocket
        DatagramSocket socket = new DatagramSocket();
        // 4. 发送数据
        socket.send(packet);
        // 客户端接收服务器端的响应
        byte[] resp = new byte[1024];
        DatagramPacket respPacket = new DatagramPacket(resp, resp.length);
        socket.receive(respPacket);
        String info = new String(resp, 0, respPacket.getLength());
        System.out.println("我是客户端，服务器响应的信息为：" + info);
    }
}
