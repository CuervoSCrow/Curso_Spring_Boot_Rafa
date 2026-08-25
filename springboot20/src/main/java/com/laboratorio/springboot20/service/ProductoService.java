package com.laboratorio.springboot20.service;

import com.laboratorio.springboot20.dto.ProductoResponse;
import com.laboratorio.springboot20.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllOrderByNombreAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(
            String nombre);
    ProductoResponse createProducto(ProductoRequest request);
    ProductoResponse updateProducto(Integer id,ProductoRequest request);
    boolean deleteProducto(Integer id);
}
