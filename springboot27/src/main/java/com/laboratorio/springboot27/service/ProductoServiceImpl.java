package com.laboratorio.springboot27.service;

import com.laboratorio.springboot27.dto.ProductoResponse;
import com.laboratorio.springboot27.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements
        ProductoService{
    private final ProductoRepository productoRepository;
//    private final Cate

    @Override
    public Optional<ProductoResponse> findProductoById(Integer id) {
        return this.productoRepository.findProductoById(id);
    }
    @Override
    public Optional<ProductoResponse> findProductoByNombre(String nombre) {
        return this.productoRepository.findProductoByNombre(nombre);
    }
}
