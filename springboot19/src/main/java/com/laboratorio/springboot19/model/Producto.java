package com.laboratorio.springboot19.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Integer id;

    @Column(name="categoria_id",nullable = false)
    private Integer categoriaId;

    @Column(name="nombre",nullable = false,
            length = 120,unique = true)
    private String nombre;

    @Column(name="precio",nullable = false)
    private Double precio;

    @Column(name="fecha_ingreso",nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="categoria_id",nullable = false,
            insertable = false, updatable = false)
    private Categoria categoria;

    @Override
    public String toString() {
        return "Producto{" +
                "fechaIngreso=" + fechaIngreso +
                ", precio=" + precio +
                ", nombre='" + nombre + '\'' +
                ", id=" + id +
                ", categoriaId=" + categoriaId +
                '}';
    }
}
