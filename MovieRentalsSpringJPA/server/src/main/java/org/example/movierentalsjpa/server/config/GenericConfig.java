package org.example.movierentalsjpa.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"org.example.movierentalsjpa.server.repository", "org.example.movierentalsjpa.server.service"})
public class GenericConfig {

}
