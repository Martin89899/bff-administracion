package com.pawpet.bffadmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private List<Product> alertasStockCritico;
    private Integer totalInsumosMedicos;
    private PatientSummary resumenPacientes;

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
