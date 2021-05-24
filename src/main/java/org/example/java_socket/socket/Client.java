package org.example.java_socket.socket;

import java.io.*;
import java.net.Socket;

/**
 * 基于 TCP 的 Socket 通信
 *
 * @author lifei
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 1. 创建 Socket 对象，指明需要连接的服务器的地址和端口号
            Socket socket = new Socket("127.0.0.1", 8888);
            // 2. 连接建立后，通过输出流向服务器发送请求信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("user:zhangsan;passwd:123456");
            pw.flush();
            socket.shutdownOutput(); // 关闭输出流
            // 3. 通过输入流获取服务器响应的信息
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String resp = null;
            while ((resp = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + resp);
            }
            socket.shutdownInput(); // 关闭输入流
            // 4. 关闭相应资源
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
