package org.example.movierentalsjpa.client.config;

import org.example.movierentalsjpa.client.service.CClientServiceImpl;
import org.example.movierentalsjpa.client.service.CMovieServiceImpl;
import org.example.movierentalsjpa.client.service.CRentalServiceImpl;
import org.example.movierentalsjpa.client.ui.Console;
import org.example.movierentalsjpa.common.IClientService;
import org.example.movierentalsjpa.common.IMovieService;
import org.example.movierentalsjpa.common.IRentalService;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@Configuration
@ComponentScan(basePackages = {"org.example.movierentalsjpa.client.config", "org.example.movierentalsjpa.client.ui"})
public class ClientAppConfig {
    /**
     * Enables placeholders usage with SpEL expressions.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    RmiProxyFactoryBean movieServiceProxy() {
        RmiProxyFactoryBean movieRmiProxyFactoryBean = new RmiProxyFactoryBean();
        movieRmiProxyFactoryBean.setServiceInterface(IMovieService.class);
        movieRmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/movieService");
        return movieRmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean clientServiceProxy() {
        RmiProxyFactoryBean clientRmiProxyFactoryBean = new RmiProxyFactoryBean();
        clientRmiProxyFactoryBean.setServiceInterface(IClientService.class);
        clientRmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/clientService");
        return clientRmiProxyFactoryBean;
    }

    @Bean
    RmiProxyFactoryBean rentalServiceProxy() {
        RmiProxyFactoryBean rentalRmiProxyFactoryBean = new RmiProxyFactoryBean();
        rentalRmiProxyFactoryBean.setServiceInterface(IRentalService.class);
        rentalRmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/rentalService");
        return rentalRmiProxyFactoryBean;
    }
}
