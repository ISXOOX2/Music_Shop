package org.example.pago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients; // <-- Importante

@SpringBootApplication
@EnableDiscoveryClient // Para que Eureka lo detecte
@EnableFeignClients    // 🚀 AGREGA ESTO: Activa el puente de Feign Client
public class PagoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagoApplication.class, args);
    }
}