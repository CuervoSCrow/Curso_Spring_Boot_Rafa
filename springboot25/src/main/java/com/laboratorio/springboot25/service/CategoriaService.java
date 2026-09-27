package com.laboratorio.springboot25.service;

import com.laboratorio.springboot25.dto.CategoriaResponse;

import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
}
