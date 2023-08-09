package com.onboard.config;

import org.h2.server.Service;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

public class H2Config {
    @Bean
    Server h2Tcp() throws SQLException {
        return Server.createTcpServer().start();
    }
}
