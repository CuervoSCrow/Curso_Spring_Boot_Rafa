package com.laboratorio.springboot20.service;

import com.laboratorio.springboot20.dto.CategoriaRequest;
import com.laboratorio.springboot20.dto.CategoriaResponse;


import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    Optional<CategoriaResponse> findCategoriaResponseById(Integer id);
    Optional<CategoriaResponse> findCategoriaResponseByNombre(String nombre);
    List<CategoriaResponse> findAllOrderByNombreAsc();
    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    CategoriaResponse createCategoria(CategoriaRequest request);
    CategoriaResponse updateCategoria(Integer id,CategoriaRequest request);
    boolean deleteCategoria(Integer id);
}
