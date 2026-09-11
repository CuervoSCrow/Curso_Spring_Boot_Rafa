package com.example.springboot22.repository;

import com.example.springboot22.dto.ProductoResponse;
import com.example.springboot22.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends
        JpaRepository<Producto, Integer> {

    @Query("""
            SELECT new com.example.springboot22.dto.ProductoResponse
            (p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
            FROM Producto p
            WHERE p.id = :id
            """)
    Optional<ProductoResponse> findProductoById(Integer id);

    @Query("""
            SELECT new com.example.springboot22.dto.ProductoResponse
            (p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
            FROM Producto p
            WHERE p.nombre = :nombre
            """)
    Optional<ProductoResponse> findProductoByNombre(String nombre);

    @Query("""
            SELECT new com.example.springboot22.dto.ProductoResponse
            (p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
            FROM Producto p
            ORDER BY p.nombre ASC
            """)
    List<ProductoResponse> findAllOrderByNombreAsc();

    @Query("""
            SELECT new com.example.springboot22.dto.ProductoResponse
            (p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
            FROM Producto p
            WHERE UPPER(p.nombre) LIKE UPPER(CONCAT('%', :infix, '%'))
            ORDER BY p.nombre ASC
            """)
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(
            @Param("infix") String infix);

    @Query("""
            SELECT new com.example.springboot22.dto.ProductoResponse
            (p.id, p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
            FROM Producto p
            WHERE p.categoriaId = :categoriaId
            ORDER BY p.nombre ASC
            """)
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(
            @Param("categoriaId") Integer categoriaId);

    long countByCategoriaId(Integer categoriaId);
}
