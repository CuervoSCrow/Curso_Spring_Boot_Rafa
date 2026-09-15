package com.example.springboot22.repository;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends
        JpaRepository<Categoria, Integer> {

    @Query("""
            SELECT new com.example.springboot22.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.id = :id
            """)
    Optional<CategoriaResponse> findCategoriaById(@Param("id") Integer id);

    @Query("""
            SELECT new com.example.springboot22.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.nombre = :nombre
            """)
    Optional<CategoriaResponse> findCategoriaByNombre(
            @Param("nombre") String nombre);

    @Query("""
            SELECT new com.example.springboot22.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            ORDER BY c.nombre ASC
            """)
    List<CategoriaResponse> findAllOrderByNombreAsc();

}
