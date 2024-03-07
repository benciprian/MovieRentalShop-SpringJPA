package org.example.movierentalsjpa.server;

import org.example.movierentalsjpa.server.config.ServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("starting server....");

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ServerConfig.class)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press Enter to stop the server.");
            scanner.nextLine();
        }
    }
}

