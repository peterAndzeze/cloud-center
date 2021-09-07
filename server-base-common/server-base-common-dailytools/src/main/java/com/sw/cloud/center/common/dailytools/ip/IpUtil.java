package com.sw.cloud.center.common.dailytools.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @className: IpUtil
 * @description: ip工具类
 * @author: sw
 * @date: 2021/9/7
 **/
public class IpUtil {
    private static Logger logger = LoggerFactory.getLogger(IpUtil.class);

    private static byte[] ip = null;
    private static String ipStr = null;

    public static byte[] getLocalIp() {
        if (ip != null) {
            return ip;
        }
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (item.isLoopback() || !item.isUp()) {
                        continue;
                    }
                    if (address.getAddress() instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();
                        ip = inet4Address.getAddress();
                        return ip;
                    }
                }
            }
            return InetAddress.getLocalHost().getAddress();
        } catch (SocketException | UnknownHostException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static String getLocalIpString() {
        if (ipStr != null) {
            return ipStr;
        }
        byte[] ip = getLocalIp();
        return (ip[0] & 0xff) + "." + (ip[1] & 0xff) + "." + (ip[2] & 0xff) + "." + (ip[3] & 0xff);
    }
}
