package com.laboratorio.springboot23.service;

import com.laboratorio.springboot23.dto.ProductoRequest;
import com.laboratorio.springboot23.dto.ProductoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllOrderByNombreAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer id);
    ProductoResponse createProducto(ProductoRequest request);
    ProductoResponse updateProducto(Integer id,ProductoRequest request);
    boolean deleteProducto(Integer id);

}
