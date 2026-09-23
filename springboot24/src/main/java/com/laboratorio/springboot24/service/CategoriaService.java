package com.laboratorio.springboot24.service;

import com.laboratorio.springboot24.dto.CategoriaResponse;

import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
}
