package com.laboratorio.springboot27.service;

import com.laboratorio.springboot27.dto.CategoriaResponse;

import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
}
