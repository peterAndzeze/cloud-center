package com.cloud.sso.core.filter;

import com.cloud.sso.core.config.SsoConfigProperties;
import com.cloud.sso.core.constant.ReturnResult;
import com.cloud.sso.core.constant.SsoConstant;
import com.cloud.sso.core.dto.SsoUserDto;
import com.cloud.sso.core.helper.SsoLoginHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @className: HttpDoRequestUtil
 * @description: 处理请求工具类
 * @author: sw
 * @date:  2021/9/7
 **/
public class HttpDoRequestUtil {
    @Autowired
    private SsoConfigProperties ssoConfigProperties;


    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    public void doRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain chain) throws ServletException, IOException {
        String userAgent = httpServletRequest.getHeader("User-Agent");

        /**
         * 得到当前请求设备信息
         */
        int requestFromEquipment = HttpUserAgentUtil.getRequestFromEquipment(userAgent);
        if(SsoConstant.USER_AGENT_TYPE_PC==requestFromEquipment){
            doPcRequest(httpServletRequest,httpServletResponse,chain);
        }else{
            doMobile(httpServletRequest,httpServletResponse,chain);
        }
    }


    /**
     * 处理pc端请求
     * @param req
     * @param res
     * @param chain
     */
    public  void doPcRequest(HttpServletRequest req,
                             HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String logoutPath=ssoConfigProperties.getLogoutPath();
        String servletPath=req.getServletPath();
        String ssoServer=ssoConfigProperties.getSsoServer();
        // logout path check
        if (logoutPath != null && logoutPath.trim().length() > 0 && logoutPath.equals(servletPath)) {
            // redirect logout
            String logoutPageUrl = ssoServer.concat(SsoConstant.SSO_LOGOUT);
            res.sendRedirect(logoutPageUrl);
            return;
        }

        // valid login user, cookie + redirect
        SsoUserDto ssoUserDto = SsoLoginHelper.loginCheck(req);

        // valid login fail
        if (ssoUserDto == null) {

            String header = req.getHeader("content-type");
            boolean isJson = header != null && header.contains("json");

            if (isJson) {
                res.setContentType("application/json;charset=utf-8");
                res.getWriter().println("{\"code\":" + SsoConstant.SSO_LOGIN_FAIL_RESULT.getCode() + ", \"msg\":\"" + SsoConstant.SSO_LOGIN_FAIL_RESULT.getMsg() + "\"}");
                return;
            } else {

                // total link
                String link = req.getRequestURL().toString();

                // redirect logout
                String loginPageUrl = ssoServer.concat(SsoConstant.SSO_LOGIN)
                        + "?" + SsoConstant.REDIRECT_URL + "=" + link;

                res.sendRedirect(loginPageUrl);
                return;
            }

        }
        // ser sso user
        req.setAttribute(SsoConstant.SSO_USER, ssoUserDto);

        // already login, allow
        chain.doFilter(req, res);
    }

    /**
     * 移动端登陆处理
     * @param request
     * @param response
     * @param chain
     */
    public void doMobile(HttpServletRequest request,HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String logoutPath=ssoConfigProperties.getLogoutPath();
        String servletPath=request.getServletPath();
        String ssoServer=ssoConfigProperties.getSsoServer();
        // logout filter
        if (logoutPath!=null
                && logoutPath.trim().length()>0
                && logoutPath.equals(servletPath)) {

            // logout
            SsoLoginHelper.logout(request);

            // response
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("{\"code\":"+ ReturnResult.SUCCESS_CODE+", \"msg\":\"\"}");
            return;
        }

        // login filter
        SsoUserDto ssoUserDto = SsoLoginHelper.loginCheck(request);
        if (ssoUserDto == null) {

            // response
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println("{\"code\":"+SsoConstant.SSO_LOGIN_FAIL_RESULT.getCode()+", \"msg\":\""+ SsoConstant.SSO_LOGIN_FAIL_RESULT.getMsg() +"\"}");
            return;
        }

        // ser sso user
        request.setAttribute(SsoConstant.SSO_USER, ssoUserDto);


        // already login, allow
        chain.doFilter(request, response);
    }
}
