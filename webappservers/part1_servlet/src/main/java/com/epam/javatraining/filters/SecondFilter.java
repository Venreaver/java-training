package com.epam.javatraining.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SecondFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecondFilter.class);

    public void init(FilterConfig filterConfig) {
        LOGGER.info("SecondFilter was initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Filtration started in SecondFilter");
        chain.doFilter(request, response);
        LOGGER.info("Filtration ended in SecondFilter");
    }

    public void destroy() {
        LOGGER.info("SecondFilter was destroyed");
    }
}
