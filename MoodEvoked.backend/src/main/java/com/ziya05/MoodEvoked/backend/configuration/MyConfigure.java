package com.ziya05.MoodEvoked.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfigure
{

    @Bean
    public MyEndpointConfigure newConfigure()
    {
        return new MyEndpointConfigure();
    }
}
