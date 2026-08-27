package com.laboratorio.springboot21.service;

import com.laboratorio.springboot21.dto.CategoriaRequest;
import com.laboratorio.springboot21.dto.CategoriaResponse;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    Optional<CategoriaResponse> findCategoriaById(Integer id);

    Optional<CategoriaResponse> findCategoriaByNombre(String nombre);

    List<CategoriaResponse> findAllOrderByNombreAsc();

    List<CategoriaResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);

    CategoriaResponse createCategoria(CategoriaRequest request);

    CategoriaResponse updateCategoria(Integer id,CategoriaRequest request);

    boolean deleteCategoria(Integer id);

}
