package com.bank.konto.middleware;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bank.konto.middleware.filter.LoggingFilter;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*"); // Für alle Pfade
        registrationBean.setOrder(1); // Reihenfolge
        return registrationBean;
    }

}