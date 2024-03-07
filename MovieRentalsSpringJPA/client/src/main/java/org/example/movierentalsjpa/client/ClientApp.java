package org.example.movierentalsjpa.client;

import org.example.movierentalsjpa.client.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApp {
    public static void main(String[] args) {
        System.out.println("client...");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example.movierentalsjpa.client.config");

        Console console = context.getBean(Console.class);
        console.runConsole();
    }
}
