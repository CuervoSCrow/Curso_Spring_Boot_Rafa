package com.laboratorio.springboot14.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter @Setter @RequiredArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    @Column(name = "nombre", nullable = false, length = 121)
    private String nombre;
    @Column(name = "precio", nullable = false)
    private double precio;
}
