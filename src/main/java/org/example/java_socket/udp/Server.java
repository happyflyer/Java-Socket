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
public class Server {
    public static void main(String[] args) throws IOException {
        // 1. 创建 DatagramSocket，指定端口号
        DatagramSocket socket = new DatagramSocket(8888);
        // 2. 创建 DatagramPacket
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        System.out.println("***服务器已经启动，开始侦听客户端的连接***");
        // 3. 接受客户端发送的数据信息
        // 此方法在接收到数据之前按一直会保持阻塞
        socket.receive(packet);
        // 4. 读取数据
        String info = new String(data, 0, packet.getLength());
        System.out.println("我是服务器，客户端说：" + info);
        // 服务器向客户端进行响应
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        byte[] resp = "你好，这是服务器对客户端的响应".getBytes();
        DatagramPacket respPacket = new DatagramPacket(resp, resp.length, address, port);
        socket.send(respPacket);
        socket.close();
    }
}
