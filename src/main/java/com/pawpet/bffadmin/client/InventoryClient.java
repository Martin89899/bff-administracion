package com.pawpet.bffadmin.client;

import com.pawpet.bffadmin.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventoryClient", url = "http://localhost:8080/api/inventory")
public interface InventoryClient {

    @GetMapping("/alerts")
    List<Product> getStockAlerts();

    @GetMapping("/products")
    List<Product> getAllProducts(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "1000") int limit,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String sortOrder
    );

    @PostMapping("/products")
    Product createProduct(@RequestBody Product product);

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable("id") Long id);

}
