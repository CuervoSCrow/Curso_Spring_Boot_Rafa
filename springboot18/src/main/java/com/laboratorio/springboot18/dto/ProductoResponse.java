package com.laboratorio.springboot18.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor
public class ProductoResponse {
    private Integer codigo;
    private Integer categoriaId;
    private String nombre;
    private Double precio;
    private LocalDate fechaIngreso;

}
