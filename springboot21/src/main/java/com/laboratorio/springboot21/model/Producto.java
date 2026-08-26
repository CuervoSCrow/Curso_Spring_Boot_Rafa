package com.laboratorio.springboot21.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.laboratorio.springboot21.dto.ProductoRequest;
import com.laboratorio.springboot21.dto.ProductoResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter @Setter @RequiredArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer id;

    @Column(name="categoria_id", nullable = false)
    private Integer categoriaId;

    @Column(name = "nombre",nullable = false,
            length = 120,unique = true)
    private String nombre;

    @Column(name = "precio",nullable = false)
    private Double precio;

    @Column(name="fecha_ingreso",nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="categoria_id",nullable = false,
                insertable = false,updatable = false)
    Categoria categoria;

    public Producto(ProductoRequest request){
        this.categoriaId = request.getCategoriaId();
        this.nombre = request.getNombre();
        this.precio = request.getPrecio();
        this.fechaIngreso = LocalDate.now();
    }

    public Producto(ProductoResponse response, ProductoRequest request){
        this.id = response.getCodigo();
        this.categoriaId = request.getCategoriaId();
        this.nombre = request.getNombre();
        this.precio = request.getPrecio();
        this.fechaIngreso = response.getFechaIngreso();
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", categoriaId=" + categoriaId +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaIngreso=" + fechaIngreso +
                ", categoria=" + categoria +
                '}';
    }
}
