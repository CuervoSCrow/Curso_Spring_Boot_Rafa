package com.example.springboot22.service;

import com.example.springboot22.dto.CategoriaResponse;

import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
}
