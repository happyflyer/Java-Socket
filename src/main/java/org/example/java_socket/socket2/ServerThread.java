package org.example.java_socket.socket2;

import java.io.*;
import java.net.Socket;

/**
 * 使用多线程实现服务端与多客户端通信
 *
 * @author lifei
 */
public class ServerThread extends Thread {
    private Socket socket;

    ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            // 3. 连接建立后，通过输入流读取客户端发送的请求信息
            is = this.socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info;
            while ((info = br.readLine()) != null) {
                System.out.println("我是服务器，客户端说：" + info);
            }
            this.socket.shutdownInput(); // 关闭输入流
            // 4. 通过输出流向客户端发送响应信息
            os = this.socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("欢迎你");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 5. 关闭相应资源
                if (pw != null) {
                    pw.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (is != null) {
                    is.close();
                }
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
