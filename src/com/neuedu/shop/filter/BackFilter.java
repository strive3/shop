package com.neuedu.shop.filter;

import com.neuedu.shop.entity.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 杜晓鹏
 * @create 2018-11-29 9:55
 * 该拦截器拦截通过的所有jsp，从session中获取admin，如果当前用户没有
 * 登陆，则拦截
 */
@WebFilter(filterName = "BackFilter", value = "*.jsp")
public class BackFilter implements Filter {
    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
/*
        1、request.getRequestURL()
        返回的是完整的url，包括Http协议，端口号，servlet名字和映射路径，但它不包含请求参数。
        2、request.getRequestURI()
        得到的是request URL的部分值，并且web容器没有decode过的
        3、request.getContextPath()
        返回 the context of the request.
        4、request.getServletPath()
        返回调用servlet的部分url.
        5、request.getQueryString()
        返回url路径后面的查询字符串
*/
       String uri = request.getRequestURI();
        if (!uri.contains("back") || uri.contains("login")) {
            chain.doFilter(req, resp);
        } else {
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
