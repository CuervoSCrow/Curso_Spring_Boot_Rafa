package com.laboratorio.springboot19.repository;

import com.laboratorio.springboot19.dto.CategoriaResponse;
import com.laboratorio.springboot19.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

    @Query("""
            SELECT new com.laboratorio.springboot19.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
                WHERE c.id = :id
            """)
    Optional<CategoriaResponse> findCategoriaResponseById(@Param("id") Integer id);

    @Query("""
            SELECT new com.laboratorio.springboot19.dto.CategoriaResponse(c.id, c.nombre)
                FROM Categoria c
                WHERE c.nombre = :nombre
            """)
    Optional<CategoriaResponse> findCategoriaResponseOneByNombre(@Param("nombre") String nombre);
}
