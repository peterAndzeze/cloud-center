package com.sw.cloud.center.common.http.okhttp;

import com.alibaba.fastjson.JSON;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @className: OkHttpTools
 * @description: ok http 工具类
 * @author: sw
 * @date: 2021/7/2
 **/
public class OkHttpTools {
    /**
     * The constant JSON.
     */
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final OkHttpTools OK_HTTP_TOOLS = new OkHttpTools();

    private static final Logger log = LoggerFactory.getLogger(OkHttpTools.class);


    private final OkHttpClient client;

    private OkHttpTools() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(5, TimeUnit.SECONDS);
        ConnectionPool connectionPool = new ConnectionPool(5000, 6000, TimeUnit.MILLISECONDS);
        builder.connectionPool(connectionPool);
        client = builder.build();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static OkHttpTools getInstance() {
        return OK_HTTP_TOOLS;
    }

    /**
     * Post string.
     *
     * @param url  the url
     * @param json the json（不传的话入参为""，不能传null）
     * @return the string
     */
    public String postJson(final String url, final String json) {
        String resultString = "";
        try {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            resultString = client.newCall(request).execute().body().string();
            // printHttpLog(url, json, resultString);
        } catch (IOException e) {
            log.error("【POST请求(参数为Json)失败】,请求地址：{},参数：{}", url, json, e);
        }
        return resultString;
    }

    /**
     * Post t.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param json     the json
     * @param classOfT the class of t
     * @return the t
     */
    public <T> T post(final String url, final String json, final Class<T> classOfT) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            final String result = response.body().string();
            return com.alibaba.fastjson.JSON.parseObject(result, classOfT);
        } catch (IOException e) {
            log.error("【POST请求(参数为Json)失败】,请求地址：{},参数：{}", url, json, e);
            throw e;
        }
    }

    /**
     * Post string.
     *
     * @param url    the url
     * @param params the params
     * @return the string
     */
    public String postMap(final String url, final Map<String, String> params) throws IOException {
        String resultString = "";
        try {
            String json = com.alibaba.fastjson.JSON.toJSONString(params);
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            resultString = client.newCall(request).execute().body().string();
        } catch (IOException e) {
            log.error("【POST请求(参数为Json)失败】,请求地址：{},参数：{}", url, params, e);
            throw e;
        }
        return resultString;
    }

    private static void printHttpLog(String url, String requestStr, String resultStr) {
        StringBuilder httpLogStr = new StringBuilder();
        httpLogStr.append("\n---------------http begin-------------------\n");
        httpLogStr.append("request path:\t\t").append(url).append("\n");
        httpLogStr.append("request param:\t\t").append(requestStr).append("\n");
        httpLogStr.append("response result:\t").append(resultStr).append("\n");
        httpLogStr.append("---------------http end-------------------\n");
        log.info(httpLogStr.toString());
    }
}
