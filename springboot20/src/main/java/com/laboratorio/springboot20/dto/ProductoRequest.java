package com.laboratorio.springboot20.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ProductoRequest {
    private Integer categoriaId;
    private String nombre;
    private Double precio;
}
