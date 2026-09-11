package com.example.springboot22.service;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.dto.ProductoRequest;
import com.example.springboot22.dto.ProductoResponse;
import com.example.springboot22.model.Producto;
import com.example.springboot22.repository.CategoriaRepository;
import com.example.springboot22.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Optional<ProductoResponse> findProductoById(Integer id) {
        return productoRepository.findProductoById(id);
    }

    @Override
    public Optional<ProductoResponse> findProductoByNombre(String nombre) {
        return this.productoRepository.findProductoByNombre(nombre);
    }

    @Override
    public List<ProductoResponse> findAllOrderByNombreAsc() {
        return this.productoRepository.findAllOrderByNombreAsc();
    }

    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre) {
        return this.productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre);
    }

    @Override
    public List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId) {
        return this.productoRepository.findByCategoriaIdOrderByNombreAsc(categoriaId);
    }

    @Override
    public ProductoResponse createProducto(Integer id, ProductoRequest request) {
        Optional<ProductoResponse> productoDB =
                this.findProductoByNombre(request.getNombre());
        if(productoDB.isPresent()){
            return productoDB.get();
        }
        Optional<CategoriaResponse> categoriaDB =
                this.categoriaRepository.findCategoriaById(request.getCategoriaId());
        if(categoriaDB.isEmpty()){
            throw new RuntimeException("No existe la categoria indicada," +
                    " no se puede crear el producto");
        }
        Producto producto = new Producto(request);
        Producto productoNuevo = this.productoRepository.save(producto);
        return new ProductoResponse(productoNuevo);
    }


}
