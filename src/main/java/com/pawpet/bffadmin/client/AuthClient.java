package com.pawpet.bffadmin.client; // Tu package actual

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

// Cambiamos el puerto al 3001 que es el real de su microservicio
@FeignClient(name = "ms-auth", url = "http://localhost:3001") 
public interface AuthClient {

    // Cambiamos la ruta exacta que sale en su documentación: /api/auth/login
    @PostMapping("/api/auth/login")
    Map<String, Object> login(@RequestBody Map<String, String> loginCredentials);
}