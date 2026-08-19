package com.laboratorio.springboot17.repository;

import com.laboratorio.springboot17.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

//    =========================== Consultas Derivadas =========================
    Optional<Producto> findOneByNombre(String nombre);
    Optional<Producto> findOneByNombreIgnoreCase(String nombre);
    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);

//    ===========================================================================
}
