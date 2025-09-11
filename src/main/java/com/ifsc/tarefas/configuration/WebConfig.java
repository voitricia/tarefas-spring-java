package com.ifsc.tarefas.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    // sobrescreve o metodo addViewControllers da interface WebMvcConfigurer
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addRedirectViewController("/", "/templates");
    }
}
