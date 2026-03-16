package com.example.polizasapi.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String API_KEY_VALUE  = "123456";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  httpRequest  = (HttpServletRequest)  request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String apiKey = httpRequest.getHeader(API_KEY_HEADER);

        if (apiKey == null || !apiKey.equals(API_KEY_VALUE)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Unauthorized: x-api-key inválido o ausente\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
