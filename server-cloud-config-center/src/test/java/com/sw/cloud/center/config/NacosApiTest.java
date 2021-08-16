package com.sw.cloud.center.config;

import com.alibaba.fastjson.JSON;
import com.sw.cloud.center.common.dailytools.text.CharsetKit;
import com.sw.cloud.center.common.http.apache.ApacheHttpClientHelper;
import com.sw.cloud.center.config.nacos.pojo.NacosApiResponseVo;
import com.sw.cloud.center.config.nacos.pojo.NameSpacePoJo;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @className: NacosApiTest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/7/6
 **/
public class NacosApiTest {
    public static String url="http://localhost:8848/nacos/";
    @Test
    public void getNameSpace(){
        String path="v1/console/namespaces";
        System.out.println(url + path);
        String s = ApacheHttpClientHelper.sendGet(url + path);

        NacosApiResponseVo nacosApiResponseVo = JSON.parseObject(s, NacosApiResponseVo.class);
        List data = nacosApiResponseVo.getData();
        for (Object n:data) {

            System.out.println(JSON.toJSONString(n));
        }
        System.out.println(nacosApiResponseVo.getData().size());
    }
    @Test
    public void createNameSpace(){
        String path="nacos/v1/console/namespaces";
        Map<String,String> params=new HashMap<>();
        params.put("customNamespaceId", UUID.randomUUID().toString());
        params.put("namespaceName","prod");
                params.put("namespaceDesc","正式环境");
        String s = ApacheHttpClientHelper.sendPostMap("http://localhost:8848/nacos/v1/console/namespaces", params, CharsetKit.UTF_8);
        System.out.println(s);
    }


}
