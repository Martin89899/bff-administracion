package com.pawpet.bffadmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Long id;
    private String sku;
    private String name;
    private String description;
    private String category; // REMEDIO, VACUNA, INSUMO_CLINICO
    private Double price;
    private Double cost;
    private Integer stock;
    private Integer minStock;
    private Integer maxStock;
    private String location;
    private String supplier;
    private Boolean isActive;
    private String unit;

    // Additional fields for frontend compatibility
    private String severity; // CRITICAL, WARNING
    private String type; // VACCINE, REMEDIO
    private Integer currentStock; // Alias for stock

    // Getters y Setters manuales para asegurar compatibilidad
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) {
        this.category = category;
        // Auto-map severity and type based on category
        if ("VACUNA".equalsIgnoreCase(category)) {
            this.severity = "CRITICAL";
            this.type = "VACCINE";
        } else if ("REMEDIO".equalsIgnoreCase(category)) {
            this.severity = "WARNING";
            this.type = "REMEDIO";
        } else {
            this.severity = "WARNING";
            this.type = "REMEDIO";
        }
    }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) {
        this.stock = stock;
        this.currentStock = stock; // Sync currentStock with stock
    }

    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }

    public Integer getMaxStock() { return maxStock; }
    public void setMaxStock(Integer maxStock) { this.maxStock = maxStock; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getCurrentStock() {
        return currentStock != null ? currentStock : stock;
    }
    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
        this.stock = currentStock; // Sync stock with currentStock
    }

}
