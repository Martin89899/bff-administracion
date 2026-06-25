package com.pawpet.bffadmin.controller;

import com.pawpet.bffadmin.client.InventoryClient;
import com.pawpet.bffadmin.client.PatientClient;
import com.pawpet.bffadmin.client.AuthClient; // <-- 1. Importamos el nuevo cliente Feign
import com.pawpet.bffadmin.dto.DashboardResponse;
import com.pawpet.bffadmin.dto.Patient;
import com.pawpet.bffadmin.dto.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;       // <-- Importado para manejar respuestas HTTP específicas
import org.springframework.http.MediaType;       // <-- Importado para MediaType
import org.springframework.http.ResponseEntity;   // <-- Importado para retornar respuestas genéricas o errores
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // <-- Importado para la ruta del login
import org.springframework.web.bind.annotation.RequestBody; // <-- Importado para recibir las credenciales
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bff/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final InventoryClient inventoryClient;
    private final PatientClient patientClient;
    private final AuthClient authClient; // <-- 2. Declaramos el cliente de autenticación como final

    // 3. Añadimos el AuthClient al constructor para mantener la inyección por constructor limpia
    public AdminController(InventoryClient inventoryClient, PatientClient patientClient, AuthClient authClient) {
        this.inventoryClient = inventoryClient;
        this.patientClient = patientClient;
        this.authClient = authClient;
    }

    // 🚀 --- ENDPOINT DE LOGIN CON DATOS SIMULADOS ---
    @PostMapping("/login")
    public ResponseEntity<?> loginAdministrativeUser(@RequestBody Map<String, String> credentials) {
        // Retornar datos simulados directamente (microservicio de autenticación no disponible)
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Simulación simple: aceptar cualquier credencial para desarrollo
        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
            Map<String, Object> simulatedResponse = Map.of(
                "token", "simulated-jwt-token-" + System.currentTimeMillis(),
                "user", Map.of(
                    "id", 1,
                    "email", email,
                    "name", "Administrador Simulado",
                    "role", "ADMIN"
                )
            );
            return ResponseEntity.ok(simulatedResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Credenciales requeridas"));
        }
    }

    // 📊 --- ENDPOINT DE DASHBOARD CON DATOS REALES ---
    @GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public DashboardResponse getDashboard() {
        try {
            log.info("Fetching dashboard data from inventory service");
            // Obtener productos con stock crítico del microservicio de inventario
            List<Product> criticalAlerts = inventoryClient.getStockAlerts();
            log.info("Received {} critical alerts from inventory service", criticalAlerts.size());

            // Total de insumos (usamos el tamaño de alertas como aproximación por ahora)
            Integer totalSupplies = criticalAlerts.size();
            log.info("Total supplies: {}", totalSupplies);

            // Resumen de pacientes (simulado por ahora, ya que patientClient no está configurado)
            DashboardResponse.PatientSummary patientSummary = new DashboardResponse.PatientSummary();
            patientSummary.setTotalMascotas(150);
            patientSummary.setTotalPropietarios(95);

            return new DashboardResponse(criticalAlerts, totalSupplies, patientSummary);
        } catch (Exception e) {
            log.error("Error fetching dashboard data from inventory service: {}", e.getMessage(), e);
            // Fallback a datos simulados si hay error de conexión
            return getSimulatedDashboard();
        }
    }

    private DashboardResponse getSimulatedDashboard() {
        log.warn("Using simulated dashboard data - connection to inventory service failed");
        // Productos simulados con stock crítico
        List<Product> criticalAlerts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Antibiótico Amoxicilina");
        product1.setCategory("REMEDIO");
        product1.setStock(5);
        product1.setMinStock(10);
        product1.setUnit("unidades");
        criticalAlerts.add(product1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Vacuna Rabia");
        product2.setCategory("VACUNA");
        product2.setStock(3);
        product2.setMinStock(8);
        product2.setUnit("dosis");
        criticalAlerts.add(product2);

        Integer totalSupplies = 25;

        DashboardResponse.PatientSummary patientSummary = new DashboardResponse.PatientSummary();
        patientSummary.setTotalMascotas(150);
        patientSummary.setTotalPropietarios(95);

        return new DashboardResponse(criticalAlerts, totalSupplies, patientSummary);
    }

    // 📦 --- ENDPOINTS DE GESTIÓN DE INVENTARIO ---

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            log.info("Fetching all products from inventory service");
            List<Product> products = inventoryClient.getAllProducts(1, 1000, "name", "asc");
            log.info("Received {} products from inventory service", products.size());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Error fetching products from inventory service: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            log.info("Creating product: {}", product.getName());
            Product createdProduct = inventoryClient.createProduct(product);
            log.info("Product created successfully with ID: {}", createdProduct.getId());
            return ResponseEntity.ok(createdProduct);
        } catch (Exception e) {
            log.error("Error creating product: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            log.info("Deleting product with ID: {}", id);
            inventoryClient.deleteProduct(id);
            log.info("Product deleted successfully");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting product: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}