package com.laboratorio.springboot25.repository;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends
        JpaRepository<Categoria, Integer> {

    @Query("""
            SELECT new com.laboratorio.springboot25.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.id = :id
            """)
    Optional<CategoriaResponse>findCategoriaById(
            @Param("id") Integer id);
    @Query("""
            SELECT new com.laboratorio.springboot25.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.nombre = :nombre
            """)
    Optional<CategoriaResponse>findCategoriaByNombre(
            @Param("nombre") String nombre);
    @Query("""
            SELECT new com.laboratorio.springboot25.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            ORDER BY c.nombre ASC
            """)
    List<CategoriaResponse> findAllOrderByNombreAsc();
}
