package org.example.descuento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DescuentoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DescuentoApplication.class, args);
    }

}
