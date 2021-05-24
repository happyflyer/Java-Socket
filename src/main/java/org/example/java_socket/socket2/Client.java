package org.example.java_socket.socket2;

import java.io.*;
import java.net.Socket;

/**
 * 使用多线程实现服务端与多客户端通信
 *
 * @author lifei
 */
public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            socket = new Socket("127.0.0.1", 8888);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("user:zhangsan;passwd:888888");
            pw.flush();
            socket.shutdownOutput(); // 关闭输出流
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String resp = null;
            while ((resp = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + resp);
            }
            socket.shutdownInput(); // 关闭输入流
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
