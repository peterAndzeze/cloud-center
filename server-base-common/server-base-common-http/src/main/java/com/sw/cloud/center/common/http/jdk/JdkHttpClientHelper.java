package com.sw.cloud.center.common.http.jdk;

import com.sw.cloud.center.common.dailytools.text.CharsetKit;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * @className: JdkHttpClientHelper
 * @description: jdk 原生
 * @author: sw
 * @date: 2021/7/3
 **/
public class JdkHttpClientHelper {

    private static Logger logger = LoggerFactory.getLogger(JdkHttpClientHelper.class);


    /**
     * send post
     * @param postUrl
     * @param params
     * @param encoding
     * @return
     */

    public static String sendPostMap(String postUrl,int connectTimeout,int readTimeOut, Map<String, Object> params, String encoding) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            HttpURLConnection httpURLConnection = createHttpURLConnection(postUrl, 100, 200, "POST");
            OutputStreamWriter wr = new OutputStreamWriter(httpURLConnection.getOutputStream());
            String content = getContent(params, encoding);
            wr.write(content);
            wr.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), encoding));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            wr.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 发送get
     * @param requestUrl
     * @param encoding
     * @return
     */
    public static String sendGet(String requestUrl, int connectTimeout,int readTimeOut,String encoding) {
        String result = "";
        try {
            HttpURLConnection httpURLConnection = createHttpURLConnection(requestUrl, 100, 200, "GET");
            if (httpURLConnection.getResponseCode() == 200) {
                InputStream is = httpURLConnection.getInputStream();
                result = formatIsToString(is, encoding);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /**
     *
     * @param params
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getContent(Map<String, Object> params, String encoding) throws UnsupportedEncodingException {
        String content = null;
        //Map.entrySet 方法返回映射的 collection 视图，其中的元素属于此类
        Set<Map.Entry<String, Object>> set = params.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> i : set) {
            //将参数解析为"name=tom&age=21"的模式
            sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue().toString(), encoding)).append("&");
        }
        if (sb.length() > 1) {
            content = sb.substring(0, sb.length() - 1);
        }
        return content;
    }

    /**
     * 请求结果
     * @param is
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String formatIsToString(InputStream is, String encoding) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len;
        try {
            while ((len = is.read(buf)) != -1) {
                byteArrayOutputStream.write(buf, 0, len);
            }
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            is.close();
        } catch (IOException e) {
            logger.error("结果获取错误:{}",e.getMessage());
        }
        return new String(byteArrayOutputStream.toByteArray(), encoding);
    }


    /********* 通用函数  ****************************/

    /**
     * 创建链接地址
     * @param url
     * @param connectTimeout
     * @param readTimeOut
     * @return
     * @throws IOException
     */
    private static HttpURLConnection createHttpURLConnection(String url,int connectTimeout,int readTimeOut,String method) throws IOException {
        URL sendUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection)sendUrl.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setReadTimeout(readTimeOut);
        urlConnection.setRequestProperty("accept", "*/*");
        urlConnection.setRequestProperty("connection", "Keep-Alive");
        urlConnection.setRequestMethod(method);
        return urlConnection;
    }




}
