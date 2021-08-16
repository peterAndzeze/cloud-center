package com.sw.cloud.common.http;

import com.sw.cloud.center.common.dailytools.text.CharsetKit;
import com.sw.cloud.center.common.http.apache.ApacheHttpClientHelper;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: HttpTest
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/7/3
 **/
public class HttpTest {
    private static final Logger logger= LoggerFactory.getLogger(HttpTest.class);
    public static void main(String[] args) throws URISyntaxException {
        String url="http://localhost:8080/test/hello";
        logger.info(url);
        long start=System.currentTimeMillis();
        List<String> result=new ArrayList<>();
        for (int i = 0; i <10000 ; i++) {
          // String s = SimpleHttp.doPost(url, new HashMap<>());
            /*Map<String,String> params=new HashMap<>();
            params.put("name","张三");*/
             String s = ApacheHttpClientHelper.sendGet(url);
            System.out.println(s);
            //String s = OkHttpTools.getInstance().postJson(url, "");
            result.add(s);
        }

        System.out.println((System.currentTimeMillis()-start)+"result:"+result.size());
    }
}

class SimpleHttp{
    /**
     * @param url
     * @param param
     * @return String
     * @Title: post请求(带参)
     * @Description:
     * @author: FLY
     * @date:2016年12月12日 下午3:56:01
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println("simple send client instance info:"+httpClient.hashCode());
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build();//设置请求和传输超时时间
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }



            // 执行http请求
            try {
                response = httpClient.execute(httpPost);
                // 使用HttpClient认证机制
                // response = httpClient.execute(httpPost, context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }
}
