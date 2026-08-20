package com.laboratorio.springboot17.repository;

import com.laboratorio.springboot17.dto.ProductoDTO;
import com.laboratorio.springboot17.dto.ProductoRecord;
import com.laboratorio.springboot17.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

//    =========================== Consultas Derivadas =========================
    Optional<Producto> findOneByNombre(String nombre);
    Optional<Producto> findOneByNombreIgnoreCase(String nombre);
    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);

//    ===========================================================================

//    =========================== Consultas JPQL =========================

    // Busca una lista de registros que la categoria sea n y el nombre contenga la variable buscar
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

//    Actualiza la categoria de un producto
    @Query("""
            UPDATE Producto p SET
                p.categoriaId = :idDestino
                WHERE p.categoriaId = :idOrigen
            """)
    @Modifying
    @Transactional
    int updateCategoriaProductos(@Param("idOrigen") Integer idOrigen,
                                 @Param("idDestino") Integer idDestino);

//    Elimina Productos por Categoriá
    @Query("""
            DELETE FROM Producto p 
            WHERE p.categoriaId = :categoriaId
            """)
    @Modifying
    @Transactional
    int deleteByProductosByCategoriaId(@Param("categoriaId") Integer categoriaId);

//    =====================================================================

//    =========================== Consultas Nativas =========================

//    Selecciona productos por categoria y nombre
    @Query(value = """
        SELECT * 
            FROM poductos
            WHERE categoria_id = :categoriaId 
            AND UPPER(nombre) LIKE UPPER('%' || :infix ||'%') 
            ORDER BY codigo ASC
        """, nativeQuery = true)
    List<Producto> findByCategoriaAndNombreSQL(
            @Param("categoriaId") Integer categoriaId,
            @Param("infix") String infix);

//    Borrado de productos por categoriaId
    @Query(value = """
        DELETE FROM productos
            WHERE categoria_id = :categoriaId
        """, nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByProductosByCategoriaIdSQL(@Param("categoriaId") Integer categoriaId);
//    =====================================================================

//    ==================== Cosultas Personalizadas =========================
//    Usando Constructor DTOS  ==========================================
    @Query("""
            SELECT new com.laboratorio.springboot17.dto.ProductoDTO
                (p.codigo, p.nombre, p.categoria.nombre)
                FROM Producto p
                ORDER BY p.nombre ASC
            """)
    List<ProductoDTO> findListadoProductosDTO();

//    Usando Record DTOS ================================================
    @Query("""
            SELECT new com.laboratorio.springboot17.dto.ProductoRecord
                (p.codigo, p.nombre, p.categoria.nombre)
                FROM Producto p
                ORDER BY p.nombre ASC
            """)
    List<ProductoRecord> findListadoProductosRecord();
//    =====================================================================
}
