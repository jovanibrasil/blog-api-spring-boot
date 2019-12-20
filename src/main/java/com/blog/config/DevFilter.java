package com.blog.config;

import org.springframework.context.annotation.Profile;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Profile("dev")
public class DevFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servReq = (HttpServletRequest) request;
        String requestURI = servReq.getRequestURI();
        if (requestURI.startsWith("/blog-api")) {
            request.getRequestDispatcher(requestURI.replace("/blog-api", "")).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
