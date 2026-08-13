package com.laboratorio.springboot14.repository;

import com.laboratorio.springboot14.modelo.Categoria;
import com.laboratorio.springboot14.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    Optional<Producto> findOneByNombre(String nombre);
    Optional<Producto> findOneByNombreIgnoreCase(String nombre);

    List<Producto> findByNombreContaining(String nombre);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<Producto> findTop3ByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    Optional<Producto> findFirstByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<Producto> findByNombreStartingWithIgnoreCaseOrderByNombreDesc(String nombre);

    List<Producto> findByCategoriaIdOrderByCodigo(Integer categoriaId);
    List<Producto> findByCategoriaAndNombreContainingIgnoreCaseOrderByNombreAsc(Categoria categoria, String nombre);

    List<Producto> findByPrecioLessThanOrderByCodigoAsc(double precio);
    List<Producto> findByPrecioGreaterThanEqualOrderByCodigoAsc(double precio);

    List<Producto> findByFechaIngresoBefore(LocalDate date);
    List<Producto> findByFechaIngresoAfter(LocalDate date);
    List<Producto> findByFechaIngresoBetween(LocalDate dateMin, LocalDate dateMax);

    List<Producto> findByCategoriaIdIn(List<Integer> ids);

}
