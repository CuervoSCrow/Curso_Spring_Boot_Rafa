package com.laboratorio.springboot21.dto;

import com.laboratorio.springboot21.model.Producto;
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

    public ProductoResponse(Producto producto){
        this.codigo=producto.getId();
        this.categoriaId=producto.getCategoriaId();
        this.nombre=producto.getNombre();
        this.precio=producto.getPrecio();
        this.fechaIngreso=producto.getFechaIngreso();
    }
}
