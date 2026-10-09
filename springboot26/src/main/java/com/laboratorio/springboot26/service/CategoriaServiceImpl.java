package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.CategoriaResponse;
import com.laboratorio.springboot26.repository.CategoriaRepository;
import com.laboratorio.springboot26.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Override
    public Optional<CategoriaResponse> findCategoriaByNombre(String nombre) {
        return this.categoriaRepository.findCategoriaByNombre(nombre);
    }
    @Override
    public List<CategoriaResponse> findAllCategoria() {
        Sort sort = Sort.by("nombre").ascending();
        return this.categoriaRepository.findAllCategoria(sort);
    }
}
