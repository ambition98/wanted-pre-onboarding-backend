package com.onboard.config;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ULIDConfig {

    @Bean
    public ULID ULIDFactory() {
        return new ULID();
    }
}
