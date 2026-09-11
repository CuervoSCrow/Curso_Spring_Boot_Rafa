package com.example.springboot22.service;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.repository.CategoriaRepository;
import com.example.springboot22.repository.ProductoRepository;
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
