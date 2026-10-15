package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.ProductoResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllProductos();
}
