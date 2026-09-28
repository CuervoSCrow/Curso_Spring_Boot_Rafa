package com.laboratorio.springboot25.service;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.repository.CategoriaRepository;
import com.laboratorio.springboot25.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    @Override
    public Optional<CategoriaResponse> findCategoriaById(Integer id) {
        return this.categoriaRepository.findCategoriaById(id);
    }
}
