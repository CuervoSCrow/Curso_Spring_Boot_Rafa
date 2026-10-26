package com.laboratorio.springboot27.service;

import com.laboratorio.springboot27.dto.ProductoResponse;
import com.laboratorio.springboot27.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Override
    public List<ProductoResponse> findAllProducto() {
        Sort sort = Sort.by("nombre").ascending();
        return this.productoRepository.findAllProducto(sort);
    }
    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCase(String infix) {
        Sort.Order order = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort sort = Sort.by(order);
        return this.productoRepository.findByNombreContainingIgnoreCase(infix,sort);
    }
    @Override
    public List<ProductoResponse> findByCategoriaId(Integer id) {
        return this.productoRepository.findByCategoriaId(id);
    }

}
