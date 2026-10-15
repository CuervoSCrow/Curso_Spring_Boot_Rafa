package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.ProductoResponse;
import com.laboratorio.springboot26.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Optional<ProductoResponse> findProductoById(Integer id) {
        return this.productoRepository.findProductoById(id);
    }
    @Override
    public Optional<ProductoResponse> findProductoByNombre(String nombre) {
        return this.productoRepository.findProductoByNombre(nombre);
    }
    @Override
    public List<ProductoResponse> findAllProductosAsc() {
        Sort.Order order = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort.Order order2 =Sort.Order.by("precio")
                .with(Sort.Direction.ASC);
        Sort sort = Sort.by(order, order2);
        return this.productoRepository.findAllProductos(sort);
    }
    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        Sort.Order order = Sort.Order.by("nombre")
                .with(Sort.Direction.ASC)
                .ignoreCase();
        Sort sort = Sort.by(order);

        return this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix,sort);
    }
    @Override
    public List<ProductoResponse> findByCategoriaId(Integer categoriaId) {
        Sort sort = Sort.by(Sort.Direction.ASC,"nombre")
                .and(Sort.by(Sort.Direction.ASC,"precio"));

        return this.productoRepository.findByCategoriaId(categoriaId, sort);
    }


}
