package com.tienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TiendaSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaSpringBootApplication.class, args);
        System.out.println("\n=======================================================");
        System.out.println("🚀 ¡Aplicación Spring Boot iniciada exitosamente!");
        System.out.println("🌐 Accede a la interfaz web en: http://localhost:8085");
        System.out.println("=======================================================\n");
    }
}
