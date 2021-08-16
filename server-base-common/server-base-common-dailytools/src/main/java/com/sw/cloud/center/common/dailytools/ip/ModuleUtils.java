package com.sw.cloud.center.common.dailytools.ip;

import com.sw.cloud.center.common.dailytools.string.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @className: ModuleUtils
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/7/3
 **/
public class ModuleUtils {
    /**
     * 提取ip
     * @param url
     * @return
     */
    public static String extractIp(String  url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        URI uri = getUri(url);
        return uri.getHost()+uri.getPath();
    }


    /**
     * 提取端口
     * @param url,格式如："http://localhost:8080/test.do"
     * @return
     */
    public static Integer extractPort(String  url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        URI uri = getUri(url);
        if(uri.getPort()!=0){
            return uri.getPort();
        }
        return 8080;
    }

    /**
     * 获取uri
     * @param url
     * @return
     */
    private static URI getUri(String url){
        try {
            URI uri = new URI(url);
            return uri;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String url="http://localhost:8080/11212";

    }


}
