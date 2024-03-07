package org.example.movierentalsjpa.server.config;

import org.example.movierentalsjpa.common.IClientService;
import org.example.movierentalsjpa.common.IMovieService;
import org.example.movierentalsjpa.common.IRentalService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.remoting.rmi.RmiServiceExporter;

@Configuration
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db.properties"),
})
@ComponentScan(basePackages = {"org.example.movierentalsjpa.server.service", "org.example.movierentalsjpa.server.config"})
public class ServerConfig {

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
    public RmiServiceExporter movieRmiServiceExporter(@Qualifier("SMovieServiceImpl") IMovieService movieService) {
        System.out.println("Exporting movieService to RMI registry");
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("movieService");
        exporter.setServiceInterface(IMovieService.class);
        exporter.setService(movieService);
        return exporter;
    }

    @Bean
    public RmiServiceExporter clientRmiServiceExporter(@Qualifier("SClientServiceImpl") IClientService clientService) {
        System.out.println("Exporting clientService to RMI registry");
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("clientService");
        exporter.setServiceInterface(IClientService.class);
        exporter.setService(clientService);
        return exporter;
    }

    @Bean
    public RmiServiceExporter rentalRmiServiceExporter(@Qualifier("SRentalServiceImpl") IRentalService rentalService) {
        System.out.println("Exporting rentalService to RMI registry");
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceName("rentalService");
        exporter.setServiceInterface(IRentalService.class);
        exporter.setService(rentalService);
        return exporter;
    }
}
