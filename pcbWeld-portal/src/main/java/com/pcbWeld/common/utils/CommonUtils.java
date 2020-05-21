package com.pcbWeld.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * 常用的工具类封装  md5 uuid等
 */
public class CommonUtils {

    /**
     * 生成uuid，用来识别一笔订单，也可用作 nonce_str
     * @return
     */
    public static String generateUUID(){
        String uuid= UUID.randomUUID().toString().
                replaceAll("-","").substring(0,32);
        return uuid;
    }

    public static String MD5(String data){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
           for(byte item:array) {
               sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1,3));
           }
           return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前的IP
     */
    public static String getIpAddr(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        if (ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }
}
