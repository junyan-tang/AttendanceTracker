package edu.duke.ece651.team4.TrackerServer.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://vcm-38068.vm.duke.edu:3000", "http://localhost:3000", "http://10.197.0.1:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
    
}
