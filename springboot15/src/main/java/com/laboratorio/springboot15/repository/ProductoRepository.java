package com.laboratorio.springboot15.repository;

import com.laboratorio.springboot15.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findByNombre(String nombre);
    Optional<Producto> findOneByNombreIgnoreCase(String nombre);

    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
}
