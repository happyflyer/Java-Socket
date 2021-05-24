package org.example.java_socket.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * URL 类
 *
 * @author lifei
 */
public class UrlDemo {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://www.baidu.com");
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String data = br.readLine();
            while (data != null) {
                System.out.println(data);
                data = br.readLine();
            }
            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
