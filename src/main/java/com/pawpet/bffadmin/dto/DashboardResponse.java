package com.pawpet.bffadmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DashboardResponse {

    private List<Product> criticalAlerts;
    private Integer totalSupplies;
    private PatientSummary patientSummary;

    public DashboardResponse(List<Product> criticalAlerts, Integer totalSupplies, PatientSummary patientSummary) {
        this.criticalAlerts = criticalAlerts;
        this.totalSupplies = totalSupplies;
        this.patientSummary = patientSummary;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatientSummary {
        private Integer totalMascotas;
        private Integer totalPropietarios;
        
        // Getters y Setters manuales para asegurar compatibilidad
        public Integer getTotalMascotas() { return totalMascotas; }
        public void setTotalMascotas(Integer totalMascotas) { this.totalMascotas = totalMascotas; }
        
        public Integer getTotalPropietarios() { return totalPropietarios; }
        public void setTotalPropietarios(Integer totalPropietarios) { this.totalPropietarios = totalPropietarios; }
    }

}
