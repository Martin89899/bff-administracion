package com.pawpet.bffadmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    
    private Long id;
    private String name;
    private String species;
    private String breed;
    private Integer age;
    private String ownerName;
    
}
