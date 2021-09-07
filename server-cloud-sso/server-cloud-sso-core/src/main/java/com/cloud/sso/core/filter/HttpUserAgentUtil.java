package com.cloud.sso.core.filter;

import com.cloud.sso.core.constant.SsoConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: HttpUserAgentUtil
 * @description: 请求端user user-agent 处理
 * @author: sw
 * @date: 2021/9/7
 **/
public class HttpUserAgentUtil {
    /**
     * 后期从参数字典里获取
     */
    private final static Map<String,Integer> userAgentMap = new HashMap<>();
    static {
        userAgentMap.put("Android", SsoConstant.USER_AGENT_TYPE_MOBILE);
        userAgentMap.put("iPhone", SsoConstant.USER_AGENT_TYPE_MOBILE);

        userAgentMap.put("iPod", SsoConstant.USER_AGENT_TYPE_MOBILE);

        userAgentMap.put("iPad", SsoConstant.USER_AGENT_TYPE_MOBILE);

        userAgentMap.put("Windows Phone", SsoConstant.USER_AGENT_TYPE_MOBILE);

        userAgentMap.put("MQQBrowser", SsoConstant.USER_AGENT_TYPE_MOBILE);

    }

    /**
     * 根据user-agent 判断端信息
     * @param userAgent 设备信息
     * @return 设备信息
     */
    public static int getRequestFromEquipment(String userAgent){
        if(userAgentMap.containsKey(userAgent)){
            return SsoConstant.USER_AGENT_TYPE_MOBILE;
        }
        return SsoConstant.USER_AGENT_TYPE_PC;
    }
}
