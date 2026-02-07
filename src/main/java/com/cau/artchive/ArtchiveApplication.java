package com.cau.artchive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ArtchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArtchiveApplication.class, args);
    }

}
