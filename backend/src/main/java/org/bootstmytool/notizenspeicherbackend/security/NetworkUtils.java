package org.bootstmytool.notizenspeicherbackend.security;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {
    public static String getLocalIpAddress() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostAddress(); // Returns something like "192.168.x.x"
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "IP not found";
        }
    }
}
