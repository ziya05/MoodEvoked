package com.ziya05.MoodEvoked.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Value("${my.result.path}")
	private String resultPath;
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //super.addViewControllers(registry);
        registry.addViewController("/ws").setViewName("/ws");
        registry.addViewController("/mood").setViewName("/mood");
        
	}
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/**")
				.addResourceLocations("file:" + resultPath);
	}

}