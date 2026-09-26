package com.laboratorio.springboot25.service;

import com.laboratorio.springboot25.dto.ProductoResponse;

import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse>findProductoById(Integer id);
}
