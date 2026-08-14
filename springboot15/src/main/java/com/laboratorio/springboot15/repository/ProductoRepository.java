package com.laboratorio.springboot15.repository;

import com.laboratorio.springboot15.model.Producto;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    Optional<Producto> findOneByNombre(String nombre);
    Optional<Producto> findOneByNombreIgnoreCase(String nombre);
    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);

    @Query("""
            SELECT p 
                FROM Producto p
                WHERE p.categoriaId = :categoriaId
                AND UPPER(p.nombre) LIKE UPPER(CONCAT('%',:infix,'%'))
                ORDER BY p.codigo ASC
            """)
    List<Producto> findByCategoriaAndNombre(
            @Param("categoriaId") Integer categoriaId,
            @Param("infix") String infix);
}
