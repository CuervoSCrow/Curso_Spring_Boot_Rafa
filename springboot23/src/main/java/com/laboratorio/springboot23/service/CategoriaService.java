package com.laboratorio.springboot23.service;

import com.laboratorio.springboot23.dto.CategoriaRequest;
import com.laboratorio.springboot23.dto.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    Optional<CategoriaResponse> findCategoriaById(Integer id);
    Optional<CategoriaResponse> findCategoriaByNombre(String nombre);
    List<CategoriaResponse> findAllOrderByNombreAsc();
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    CategoriaResponse createCategoria(CategoriaRequest request);
    CategoriaResponse updateCategoria(Integer id, CategoriaRequest request);
    boolean deleteCategoria(Integer id);
}
