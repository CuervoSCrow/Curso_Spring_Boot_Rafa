package com.laboratorio.springboot27.service;

import com.laboratorio.springboot27.dto.ProductoResponse;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllProducto();
    List<ProductoResponse> findByNombreContainingIgnoreCase(String infix);
    List<ProductoResponse> findByCategoriaId(Integer id);
}
