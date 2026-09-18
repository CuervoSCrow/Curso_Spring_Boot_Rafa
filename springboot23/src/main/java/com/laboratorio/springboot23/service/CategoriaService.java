package com.laboratorio.springboot23.service;

import com.laboratorio.springboot23.dto.CategoriaResponse;

import java.util.Optional;

public interface CategoriaService {

    Optional<CategoriaResponse> findCategoriaById(Integer id);
}
