package com.ai.ch.user.web.filter;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by jackieliu on 16/6/18.
 */
public class TestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String detail = httpRequest.getParameter("detailConVal");
        if (StringUtils.isNotBlank(detail)){
            System.out.println(detail);
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
