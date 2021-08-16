package com.sw.cloud.center.common.http.apache;

import com.sw.cloud.center.common.dailytools.text.CharsetKit;
import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @className: ApacheHttpClientHelper
 * @description: apache
 * @author: sw
 * @date: 2021/7/3
 **/
public class ApacheHttpClientHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApacheHttpClientHelper.class);
    /**
     * 长链接保持对的时长 单位（毫秒）
     */
    private static final long KEEP_ALIVE_DURATION = 60000;
    /**
     * 客户端和服务器建立链接的超时时间  单位毫秒
     */
    private static final int CONNECT_TIMEOUT = 2000;
    /**
     * 建立链接后，客户端从服务端读取数据的超时时间 单位毫秒
     */
    private static final int SOCKET_TIMEOUT = 2000;
    /**
     * 从链接池取链接实例对的超时时间 单位毫秒
     */
    private static final int REQUEST_TIMEOUT = 2000;
    /**
     * 无效链接的清除时间间隔 单位毫秒
     */
    private static final int EXPIRED_CHECK_GAP = 6000;
    /**
     * 链接池内对不活跃对链接对的检查间隔 单位毫秒
     */
    private static final int VALIDATE_AFTER_INACTIVITY = 2000;
    /**
     * 最大链接数
     */
    private static final int POOL_MAX_TOTAL = 500;
    /**
     * 最大路由
     */
    private static final int MAX_PER_ROUTE = 500;
    /**
     * 单例 长链接管理器 ，链接池
     */
    private static PoolingHttpClientConnectionManager httpClientConnectionManager;
    /**
     * 单例 全局池化Http客户端实例
     */
    private static CloseableHttpClient closeableHttpClient;
    /**
     * 线程池：负责http链接池对的无效链接清理
     */
    private static ScheduledExecutorService monitorExecutor = null;


    /**
     * 使用连接池中的请求发送
     *
     * @param url    连接地址
     * @param params 参数
     * @return 请求字符串
     */
    public static String sendPostMap(String url, Map<String, String> params, String encoding) {
        //取得连接池
        CloseableHttpClient client = pooledHttpClient();
        HttpPost httpPost = new HttpPost(url);
        setPostParams(httpPost, params, encoding);
        return poolRequestData(url,client, httpPost);
    }

    /**
     * 使用连接池中的请求发送
     *
     * @param url    连接地址
     * @param params 参数
     * @return 请求字符串
     */
    public static String sendPostString(String url, String params, String encoding) {
        //取得连接池
        CloseableHttpClient client = pooledHttpClient();
        LOGGER.info("send client instance info:{}", client.hashCode());
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity = new StringEntity(params, encoding);
        httpPost.setEntity(httpEntity);
        return poolRequestData(url,client, httpPost);
    }

    /**
     * 使用带连接池的HTTP客户端，发送GET请求
     *
     * @param url 连接地址
     * @return 请求字符串
     */
    public static String sendGet(String url) {
        //1 取得带连接池的客户端
        CloseableHttpClient client = pooledHttpClient();
        //2 创建一个HTTP请求实例
        HttpGet httpGet = new HttpGet(url);
        //3 使用带连接池的HTTP客户端，发送请求，并且获取结果
        return poolRequestData(url,client, httpGet);
    }


    /**
     * 设置post请求的参数
     *
     * @param httpPost 主机ip和端口
     * @param params   请求参数
     */
    private static void setPostParams(HttpPost httpPost, Map<String, String> params, String encoding) {
        if (null == params) {
            return;
        }
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            list.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, encoding));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化 链接池信息
     */
    private static void createHttpClientConnectionManager() {
        /**
         * DNS 解析器
         */
        DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
        /**
         * 负责http 传输对的套接字工厂
         */
        ConnectionSocketFactory connectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        /**
         * 负责https传输对的安全套接字工厂
         */
        LayeredConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
        /**
         * 根据应用层协议，为其注册传输层的套接字工厂
         */
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", connectionSocketFactory)
                .register("https", sslSocketFactory)
                .build();

        /**
         * 创建链接管理器
         */
        httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry, null,
                null, dnsResolver, KEEP_ALIVE_DURATION,
                TimeUnit.MILLISECONDS);
        /**
         * 链接池内，链接不活跃多长时间后，需要进行一次验证 默认2秒，
         */
        httpClientConnectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);

        /**
         * 最大链接数，高于这个值时新链接请求，需要阻塞和排队等待
         */
        httpClientConnectionManager.setMaxTotal(POOL_MAX_TOTAL);
        /**
         * 设置每个route 默认对的最大链接数，路由是对MAX TOTAL的细分
         * 每个路由实际最大链接数默认值由 DefaultMaxPerRouter控制
         * MaxPerRouter 设置过小，无法支持大并发
         */
        httpClientConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
    }

    /**
     * 创建带连接池的 httpClient 客户端
     */
    public static CloseableHttpClient pooledHttpClient() {
        if (null != closeableHttpClient) {
            return closeableHttpClient;
        }
        createHttpClientConnectionManager();
        LOGGER.info(" Apache httpclient 初始化HTTP连接池  starting===");
        //请求配置实例
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        // 等待数据超时设置
        requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT);
        // 连接超时设置
        requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT);
        //从连接池获取连接的等待超时时间设置
        requestConfigBuilder.setConnectionRequestTimeout(REQUEST_TIMEOUT);
        RequestConfig config = requestConfigBuilder.build();

        // httpclient建造者实例
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //设置连接池管理器
        httpClientBuilder.setConnectionManager(httpClientConnectionManager);
        //设置请求配置信息
        httpClientBuilder.setDefaultRequestConfig(config);

        //httpclient默认提供了一个Keep-Alive策略
        //这里进行定制：确保客户端与服务端在长连接的保持时长一致
        httpClientBuilder.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                //获取响应头中HTTP.CONN_KEEP_ALIVE中的“Keep-Alive”部分值
                //如服务端响应“Keep-Alive: timeout=60”，表示服务端保持时长为60秒
                //则客户端也设置连接的保持时长为60秒
                //目的：确保客户端与服务端在长连接的保持时长一致
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (final NumberFormatException ignore) {
                        }
                    }
                }
                //如果服务端响应头中没有设置保持时长，则使用客户端统一定义时长为600s
                return KEEP_ALIVE_DURATION;
            }
        });
        //实例化：全局的池化HTTP客户端实例
        closeableHttpClient = httpClientBuilder.build();
        LOGGER.info(" Apache httpclient 初始化HTTP连接池  finished===");
        //启动定时处理线程：对异常和空闲连接进行关闭
        startExpiredConnectionsMonitor();
        return closeableHttpClient;
    }

    /**
     * 定时处理线程：对异常和空闲连接进行关闭
     */
    private static void startExpiredConnectionsMonitor() {
        //空闲监测,配置文件默认为6s,生产环境建议稍微放大一点
        int idleCheckGap = EXPIRED_CHECK_GAP;
        // 设置保持连接的时长,根据实际情况调整配置
        long keepAliveTimeout = KEEP_ALIVE_DURATION;
        //开启监控线程,对异常和空闲线程进行关闭
        monitorExecutor = new ScheduledThreadPoolExecutor(1);
        monitorExecutor.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //关闭异常连接
                httpClientConnectionManager.closeExpiredConnections();
                //关闭keepAliveTimeout（保持连接时长）超时的不活跃的连接
                httpClientConnectionManager.closeIdleConnections(keepAliveTimeout, TimeUnit.MILLISECONDS);
                //获取连接池的状态
                PoolStats status = httpClientConnectionManager.getTotalStats();
                //输出连接池的状态,仅供测试使用
                /*
                log.info(" manager.getRoutes().size():" + manager.getRoutes().size());
                log.info(" status.getAvailable():" + status.getAvailable());
                log.info(" status.getPending():" + status.getPending());
                log.info(" status.getLeased():" + status.getLeased());
                log.info(" status.getMax():" + status.getMax());
                */
            }
        }, idleCheckGap, idleCheckGap, TimeUnit.MILLISECONDS);
    }

    /**
     * 使用带连接池的HTTP客户端，发送请求
     * @param url
     * @param client  客户端
     * @param request post、get或者其他请求
     * @return 响应字符串
     */
    private static String poolRequestData(String url,CloseableHttpClient client, HttpRequest request) {
        CloseableHttpResponse response = null;
        InputStream in = null;
        String result = null;
        try {
            //执行HTTP请求
            HttpHost host = getHost(url);
            response = client.execute(host,request, HttpClientContext.create());
            //获取HTTP响应
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                in = entity.getContent();
                result = IOUtils.toString(in, CharsetKit.UTF_8);
            }
        } catch (IOException e) {
            LOGGER.error("url:{} send error:{}", url, e.getMessage());
        } finally {
            quietlyClose(in);
            quietlyClose(response);
            //无论执行成功或出现异常，HttpClient 都会自动处理并保证释放连接。
        }

        return result;
    }

    /**
     * 从url中获取HttpHost实例，含主机和端口
     *
     * @param url url 地址
     * @return HttpHost
     */
    private static HttpHost getHost(String url) {
        String hostName = url.split("/")[2];
        int port = 80;
        if (hostName.contains(":")) {
            String[] args = hostName.split(":");
            hostName = args[0];
            port = Integer.parseInt(args[1]);
        }
        HttpHost httpHost = new HttpHost(hostName, port);
        return httpHost;
    }

    /**
     * 安静的关闭可关闭对象
     *
     * @param closeable 可关闭对象
     */
    private static void quietlyClose(java.io.Closeable closeable) {
        if (null == closeable) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
