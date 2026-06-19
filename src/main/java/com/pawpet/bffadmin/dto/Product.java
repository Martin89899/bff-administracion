package com.pawpet.bffadmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    private Long id;
    private String name;
    private String category; // REMEDIO, VACUNA, INSUMO_CLINICO
    private Integer currentStock;
    private Integer minStock;
    private String unit;
    
}
