package com.laboratorio.springboot17.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class ProductoDTO {
    private Integer codigo;
    private String nombre;
    private String categoria;

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
