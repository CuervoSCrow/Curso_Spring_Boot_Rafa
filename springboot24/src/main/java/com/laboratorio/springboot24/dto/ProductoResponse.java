package com.laboratorio.springboot24.dto;

import com.laboratorio.springboot24.model.Producto;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class ProductoResponse {
    private Integer codigo;
    private Integer categoriaId;
    private String nombre;
    private Double precio;
    private LocalDate fechaIngreso;

    public ProductoResponse(Producto producto) {
        this.codigo = producto.getId();
        this.categoriaId = producto.getCategoriaId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.fechaIngreso = producto.getFechaIngreso();
    }


}
