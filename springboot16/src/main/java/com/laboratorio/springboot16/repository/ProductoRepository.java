package com.laboratorio.springboot16.repository;

import com.laboratorio.springboot16.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

//    Consultas Derivadas  *********************************

    Optional<Producto> findOneByNombre(String nombre);
    Optional<Producto> findOneByNombreIgnoreCase(String nombre);
    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);

//    Consultas JPQL *********************************

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

    @Query("""
                UPDATE Producto p SET
                    p.categoriaId = :idDestino
                    WHERE p.categoriaId = :idOrigen
           """)
    @Modifying
    @Transactional
    int updateCategoriaProductos(@Param("idOrigen") Integer idOrigen,
                                 @Param("idDestino") Integer idDestino);

    @Query("""
            DELETE FROM Producto p
            WHERE p.categoriaId = :categoriaId
            """)
    @Modifying
    @Transactional
    int deleteProductosByCategoria(@Param("categoriaId") Integer categoriaId);

    @Transactional
    long deleteByCategoriaId(Integer categoriaId);

//    Consultas Nativas  *********************************

//================================================================================
    @Query(value = """
            SELECT *
                FROM productos
                WHERE categoria_id = :categoriaId
                AND UPPER(nombre) LIKE UPPER('%' || :infix || '%')
                ORDER BY codigo ASC
                
            """, nativeQuery = true)
    List<Producto> findByCategoriaAndNombreSQL(
            @Param("categoriaId") Integer categoriaId,
            @Param("infix") String infix
    );
    //================================================================================

    //================================================================================
    @Query(value= """
            UPDATE productos
                SET categoria_id = :idDestino
                WHERE categoria_id = :idOrigen
            """, nativeQuery = true)
    @Modifying
    @Transactional
    int updateCategoriaProductosSQL(@Param("idOrigen") Integer idOrigen,
                                    @Param("idDestino") Integer idDestino);
    //================================================================================

    //================================================================================
    @Query(value="""
            DELETE FROM productos
                WHERE categoria_id = :categoriaId
            """, nativeQuery = true)
    @Modifying
    @Transactional
    int deleteProductosByCategoriaSQL(@Param("categoriaId") int categoriaId);
    //================================================================================

}
