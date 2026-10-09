package com.laboratorio.springboot26.repository;

import com.laboratorio.springboot26.dto.CategoriaResponse;
import com.laboratorio.springboot26.model.Categoria;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends
        JpaRepository<Categoria, Integer> {
    @Query("""
            SELECT new com.laboratorio.springboot26.dto.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.id = :id
            """)
    Optional<CategoriaResponse> findCategoriaById(@Param("id") Integer id);
    @Query("""
            SELECT new com.laboratorio.springboot26.dto.CategoriaResponse
            (c.id,c.nombre)
            FROM Categoria c
            WHERE c.nombre = :nombre
            """)
    Optional<CategoriaResponse> findCategoriaByNombre(@Param("nombre") String nombre);
    @Query("""
            SELECT new com.laboratorio.springboot26.dto.CategoriaResponse
            (c.id,c.nombre)
            FROM Categoria c
            """)
    List<CategoriaResponse> findAllCategoria(Sort sort);
}
