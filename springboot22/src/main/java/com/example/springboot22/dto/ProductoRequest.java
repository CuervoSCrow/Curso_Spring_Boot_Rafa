package com.example.springboot22.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ProductoRequest {
    private Integer categoriaId;
    private String nombre;
    private Double precio;
}
