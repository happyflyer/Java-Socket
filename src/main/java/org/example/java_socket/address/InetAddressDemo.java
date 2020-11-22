package org.example.java_socket.address;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * InetAddress 类
 *
 * @author lifei
 */
public class InetAddressDemo {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address.getHostName());
        System.out.println(address.getHostAddress());
        System.out.println(Arrays.toString(address.getAddress()));
        System.out.println(address);
    }
}
