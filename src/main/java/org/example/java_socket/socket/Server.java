package org.example.java_socket.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于 TCP 的 Socket 通信
 *
 * @author lifei
 */
public class Server {
    public static void main(String[] args) {
        try {
            // 1. 创建 ServerSocket 对象，绑定监听端口
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("***服务器已经启动，开始侦听客户端的连接***");
            // 2. 通过 accept() 方法监听客户端请求
            Socket socket = serverSocket.accept();
            // 3. 连接建立后，通过输入流读取客户端发送的请求信息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是服务器，客户端说：" + info);
            }
            socket.shutdownInput(); // 关闭输入流
            // 4. 通过输出流向客户端发送响应信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("欢迎你");
            pw.flush();
            // 5. 关闭相应资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
