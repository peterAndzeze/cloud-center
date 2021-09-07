package com.cloud.sso.core.filter;

import com.cloud.sso.core.config.SsoConfigProperties;
import com.cloud.sso.core.constant.SsoConstant;
import com.cloud.sso.core.path.impl.AntPathMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: SsoCoreFilter
 * @description: 核心过滤器
 * @author: sw
 * @date: 2021/9/7
 **/
public class SsoCoreFilter extends HttpServlet implements Filter {
    private static final long serialVersionUID = 3720958781588428864L;
    private static final Logger logger = LoggerFactory.getLogger(SsoCoreFilter.class);
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private SsoConfigProperties ssoConfigProperties;
    @Autowired
    private HttpDoRequestUtil httpDoRequestUtil;

    /**
     * 链路跳转处理
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        /**
         * 获取设备
         */
        String servletPath = httpServletRequest.getServletPath();

        String excludedPaths = ssoConfigProperties.getExcludedPaths();
        if (null != excludedPaths && excludedPaths.trim().length() > 0) {
            String[] split = excludedPaths.split(",");
            for (String excludedPath : split) {
                String uriPattern = excludedPath.trim();
                // 支持ANT表达式
                if (antPathMatcher.match(uriPattern, servletPath)) {
                    // excluded path, allow
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
      httpDoRequestUtil.doRequest(httpServletRequest,httpServletResponse,chain);

    }

}
