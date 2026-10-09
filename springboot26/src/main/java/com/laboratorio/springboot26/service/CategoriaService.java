package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.CategoriaResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
    Optional<CategoriaResponse> findCategoriaByNombre(String nombre);
    List<CategoriaResponse> findAllCategoria();
}
