package com.example.springboot22.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ProductoRequest {
    private Integer categoriaId;
    private String nombre;
    private Double precio;
}
