package com.laboratorio.springboot27.service;

import com.laboratorio.springboot27.dto.CategoriaResponse;

import java.util.Optional;

public class CategoriaServiceImpl implements
    CategoriaService{

    @Override
    public Optional<CategoriaResponse> findCategoriaById(Integer id) {
        return Optional.empty();
    }
}
