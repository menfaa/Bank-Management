package com.bank.kunde.middleware.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Ein Servlet-Filter, der alle eingehenden HTTP-Requests protokolliert.
 * Gibt die URI jeder Anfrage auf der Konsole aus.
 */
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Wandelt das generische ServletRequest in ein HttpServletRequest um
        HttpServletRequest req = (HttpServletRequest) request;
        // Gibt die URI der eingehenden Anfrage auf der Konsole aus
        System.out.println("[LOG] Request URI: " + req.getRequestURI());
        // Gibt die Anfrage an den nächsten Filter oder das Ziel weiter
        chain.doFilter(request, response); // WICHTIG: Weitergeben!
    }
}