package com.laboratorio.springboot19.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class ProductoResponse {

    public ProductoResponse(Integer codigo, Integer categoriaId, String nombre) {
        this.codigo = codigo;
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.precio = null;
        this.fechaIngreso = null;
    }
    private Integer codigo;
    private Integer categoriaId;
    private String nombre;
    private Double precio;
    private LocalDate fechaIngreso;

}
