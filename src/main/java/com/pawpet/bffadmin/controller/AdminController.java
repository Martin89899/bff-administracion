package com.pawpet.bffadmin.controller;

import com.pawpet.bffadmin.client.InventoryClient;
import com.pawpet.bffadmin.client.PatientClient;
import com.pawpet.bffadmin.dto.DashboardResponse;
import com.pawpet.bffadmin.dto.Patient;
import com.pawpet.bffadmin.dto.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bff/admin")
public class AdminController {

    private final InventoryClient inventoryClient;
    private final PatientClient patientClient;

    public AdminController(InventoryClient inventoryClient, PatientClient patientClient) {
        this.inventoryClient = inventoryClient;
        this.patientClient = patientClient;
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {
        try {
            List<Product> products = inventoryClient.getAllProducts();
            List<Patient> patients = patientClient.getAllPatients();
            
            // Filtrar productos con stock crítico (stock <= minStock)
            List<Product> alertasStockCritico = products.stream()
                    .filter(product -> product.getCurrentStock() != null && 
                                       product.getMinStock() != null && 
                                       product.getCurrentStock() <= product.getMinStock())
                    .collect(Collectors.toList());
            
            // Calcular total de insumos médicos
            Integer totalInsumosMedicos = products.size();
            
            // Crear resumen de pacientes
            DashboardResponse.PatientSummary resumenPacientes = new DashboardResponse.PatientSummary();
            resumenPacientes.setTotalMascotas(patients.size());
            resumenPacientes.setTotalPropietarios((int) patients.stream()
                    .map(Patient::getOwnerName)
                    .distinct()
                    .count());
            
            return new DashboardResponse(alertasStockCritico, totalInsumosMedicos, resumenPacientes);
        } catch (Exception e) {
            // Retornar datos simulados si los microservicios no están disponibles
            return getSimulatedDashboard();
        }
    }

    private DashboardResponse getSimulatedDashboard() {
        // Productos simulados con stock crítico
        List<Product> alertasStockCritico = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Antibiótico Amoxicilina");
        product1.setCategory("REMEDIO");
        product1.setCurrentStock(5);
        product1.setMinStock(10);
        product1.setUnit("unidades");
        alertasStockCritico.add(product1);
        
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Vacuna Rabia");
        product2.setCategory("VACUNA");
        product2.setCurrentStock(3);
        product2.setMinStock(8);
        product2.setUnit("dosis");
        alertasStockCritico.add(product2);
        
        // Total de insumos médicos simulado
        Integer totalInsumosMedicos = 25;
        
        // Resumen de pacientes simulado
        DashboardResponse.PatientSummary resumenPacientes = new DashboardResponse.PatientSummary();
        resumenPacientes.setTotalMascotas(150);
        resumenPacientes.setTotalPropietarios(95);
        
        return new DashboardResponse(alertasStockCritico, totalInsumosMedicos, resumenPacientes);
    }

}
