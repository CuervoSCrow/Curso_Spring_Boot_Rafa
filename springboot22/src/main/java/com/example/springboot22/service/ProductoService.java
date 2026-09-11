package com.example.springboot22.service;

import com.example.springboot22.dto.ProductoRequest;
import com.example.springboot22.dto.ProductoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllOrderByNombreAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId);
    ProductoResponse createProducto(ProductoRequest request);

}
