package com.laboratorio.springboot26.dto;


import com.laboratorio.springboot26.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductoResponse {
    private Integer codigo;
    private Integer categoriaId;
    private String nombre;
    private Double precio;
    private LocalDate fechaIngreso;


    public ProductoResponse(Producto producto) {
        this.codigo = producto.getId();
        this.categoriaId = producto.getCategoria().getId();
        this.nombre = producto.getNombre();
        this.precio = producto.getPrecio();
        this.fechaIngreso = producto.getFechaIngreso();
    }
}
