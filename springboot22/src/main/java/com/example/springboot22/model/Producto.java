package com.example.springboot22.model;

import com.example.springboot22.dto.ProductoRequest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "categoria_id",nullable=false)
    private Integer categoriaId;

    @Column(name = "nombre",nullable=false,
            length = 120, unique = true)
    private String nombre;

    @Column(name="precio",nullable = false)
    private Double precio;

    @Column(name="fecha_ingreso",nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "categoria_id",nullable = false,
                    insertable = false, updatable = false)
    private Categoria categoria;

    public Producto(ProductoRequest request){
        this.categoriaId = request.getCategoriaId();
        this.nombre = request.getNombre();
        this.precio = request.getPrecio();
        this.fechaIngreso = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", categoriaId=" + categoriaId +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}
