package com.laboratorio.springboot25.service;

import com.laboratorio.springboot25.dto.ProductoResponse;
import com.laboratorio.springboot25.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    private final ProductoRepository productoRepository;
    @Override
    public Optional<ProductoResponse> findProductoById(Integer id) {
        return productoRepository.findProductoById(id);
    }

    @Override
    public Optional<ProductoResponse> findProductoByNombre(String nombre) {
        return productoRepository.findProductoByNombre(nombre);
    }

    @Override
    public List<ProductoResponse> findAllOrderByNombreAsc() {
        return productoRepository.findAllOrderByNombreAsc();
    }
}
