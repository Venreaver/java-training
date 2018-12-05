package com.epam.javatraining.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HelloServletContextListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServletContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Servlet Context was initialized");
        LOGGER.info("Server information: {}", (sce.getServletContext().getServerInfo()));
    }

    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Servlet Context was destroyed");
    }
}
