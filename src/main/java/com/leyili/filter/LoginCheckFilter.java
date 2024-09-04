package com.leyili.filter;


import com.alibaba.fastjson.JSONObject;
import com.leyili.pojo.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 检查用户是否已经登录
 * */
@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        log.info("拦截到请求:{}",request.getRequestURI());
        //1 获取本次请求的URL
        String url = request.getRequestURI();

        //2 判断请求url中是否包含login,如果包含,说明是登录操作,放行
        if(url.contains("login")||url.contains("logout")||url.contains("backend")||url.contains("front")||url.contains("user/sendMsg")||url.contains("user/login")){
            log.info("本次请求{}无需处理,放行....",url);
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //3-1.判断登录状态
        if (request.getSession().getAttribute("employee")!=null){
            log.info("登录成功状态,放行");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        //3-2.判断移动端用户登录状态
        if (request.getSession().getAttribute("user")!=null){
            log.info("用户:{}登录成功状态,放行",request.getSession().getAttribute("user"));

            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }


        //4 未登录 返回未登录信息,通过输出流方式向客户端页面响应数据
            log.info("未登录,返回未登录错误信息");
            response.getWriter().write(JSONObject.toJSONString(Result.error("NOTLOGIN")));
    }
}
