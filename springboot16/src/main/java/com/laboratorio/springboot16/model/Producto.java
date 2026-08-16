package com.laboratorio.springboot16.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Getter @Setter @NoArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name="categoria_id",nullable = false)
    private Integer categoriaId;

    @Column(name = "nombre", nullable = false,
            length = 120, unique = true)
    private String nombre;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false,
                insertable = false)
    private Categoria categoria;

    @Override
    public String toString() {
        return "Producto{" +
                "codigo=" + codigo +
                ", categoriaId=" + categoriaId +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaIngreso=" + fechaIngreso +
                '}';
    }
}
