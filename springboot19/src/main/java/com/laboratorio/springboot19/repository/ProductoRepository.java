package com.laboratorio.springboot19.repository;

import com.laboratorio.springboot19.dto.ProductoResponse;
import com.laboratorio.springboot19.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Integer> {

    @Query("""
            SELECT new com.laboratorio.springboot19.dto.ProductoResponse
                (p.id , p.categoriaId, p.nombre, p.precio, p.fechaIngreso)
                FROM Producto p
                WHERE p.id = :id
            """)
    Optional<ProductoResponse> findProductoById(@Param("id") Integer id);

    @Query("""
            SELECT new com.laboratorio.springboot19.dto.ProductoResponse
                (p.id,p.categoriaId,p.nombre)
                FROM Producto p
                WHERE p.nombre = :nombre
            """)
    Optional<ProductoResponse> findProductoByNombre(@Param("nombre") String nombre);



}
