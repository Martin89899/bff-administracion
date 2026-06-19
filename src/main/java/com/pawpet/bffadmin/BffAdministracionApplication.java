package com.pawpet.bffadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BffAdministracionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffAdministracionApplication.class, args);
    }

}
