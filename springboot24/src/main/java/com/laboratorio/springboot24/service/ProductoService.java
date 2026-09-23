package com.laboratorio.springboot24.service;

import com.laboratorio.springboot24.dto.ProductoRequest;
import com.laboratorio.springboot24.dto.ProductoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByName(String nombre);
    List<ProductoResponse> findAllOrderByNombreAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer id);
    ProductoResponse createProduct(ProductoRequest request);
    ProductoResponse updateProduct(Integer id,ProductoRequest request);
    boolean deleteProduct(Integer id);
}

