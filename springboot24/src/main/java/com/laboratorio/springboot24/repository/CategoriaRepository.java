package com.laboratorio.springboot24.repository;

import com.laboratorio.springboot24.dto.CategoriaResponse;
import com.laboratorio.springboot24.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends
        JpaRepository<Categoria, Integer> {

    @Query("""
            SELECT new com.laboratorio.springboot24.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.id = :id
            """)
    Optional<CategoriaResponse> fidnCategoriaById(@Param("id") Integer id);
    @Query("""
            SELECT new com.laboratorio.springboot24.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.nombre = :nombre
            """)
    Optional<CategoriaResponse> findCategoriaByNombre(@Param("nombre") String nombre);
    @Query("""
            SELECT new com.laboratorio.springboot24.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            ORDER BY c.nombre ASC
            """)
    List<CategoriaResponse> findAllOrderByNombreAsc();
    @Query("""
            SELECT new com.laboratorio.springboot24.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE UPPER(c.nombre) LIKE UPPER(CONCAT('%',:nombre,'%'))
            """)
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(
            @Param("nombre") String nombre);

}
