package com.pawpet.bffadmin.client;

import com.pawpet.bffadmin.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "inventoryClient", url = "http://localhost:8080/api/inventory")
public interface InventoryClient {

    @GetMapping("/products")
    List<Product> getAllProducts();

}
