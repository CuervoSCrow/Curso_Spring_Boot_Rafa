package com.laboratorio.springboot24.service;

import com.laboratorio.springboot24.dto.CategoriaRequest;
import com.laboratorio.springboot24.dto.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    Optional<CategoriaResponse> findCategoriaById(Integer id);
    Optional<CategoriaResponse> findCategoriaByNombre(String nombre);
    List<CategoriaResponse> findAllOrderByNombreAsc();
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    CategoriaResponse createCategoria(CategoriaRequest categoria);
    CategoriaResponse updateCategoria(Integer id,CategoriaRequest categoria);
    boolean deleteCategoria(Integer id);
}
