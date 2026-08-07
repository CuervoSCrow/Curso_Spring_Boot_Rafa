package com.laboratorio.springboot10.modelo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Producto {
    private Integer codigo;
    private String nombre;
    private Double precio;
}
