package com.laboratorio.springboot21.repository;

import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.model.Categoria;
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
            SELECT new com.laboratorio.springboot21.model.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.id = :id
            """)
    Optional<Categoria> findCategoriaById(Integer id);

    @Query("""
            SELECT new com.laboratorio.springboot21.model.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE c.nombre = :nombre
            """)
    Optional<Categoria> findCategoriaByNombre(String nombre);

    @Query("""
            SELECT new com.laboratorio.springboot21.model.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            ORDER BY c.nombre ASC
            """)
    List<CategoriaResponse> findAllOrderByNombreAsc();

    @Query("""
            SELECT new com.laboratorio.springboot21.model.CategoriaResponse
            (c.id, c.nombre)
            FROM Categoria c
            WHERE UPPER(c.nombre) LIKE UPPER(CONCAT('%',:nombre,'%'))
            ORDER BY c.nombre ASC
            """)
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(
            @Param("nombre") String nombre);
}
