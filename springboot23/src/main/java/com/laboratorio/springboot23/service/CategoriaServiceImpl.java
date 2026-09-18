package com.laboratorio.springboot23.service;

import com.laboratorio.springboot23.dto.CategoriaResponse;
import com.laboratorio.springboot23.repository.CategoriaRepository;
import com.laboratorio.springboot23.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService{

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Optional<CategoriaResponse> findCategoriaById(Integer id) {
        return this.categoriaRepository.findCategoriaById(id);
    }
}
