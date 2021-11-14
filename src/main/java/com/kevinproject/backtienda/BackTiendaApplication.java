package com.kevinproject.backtienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackTiendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackTiendaApplication.class, args);
    }

}
