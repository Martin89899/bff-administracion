package com.pawpet.bffadmin.controller;

import com.pawpet.bffadmin.client.InventoryClient;
import com.pawpet.bffadmin.client.PatientClient;
import com.pawpet.bffadmin.dto.DashboardResponse;
import com.pawpet.bffadmin.dto.Patient;
import com.pawpet.bffadmin.dto.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }

}
