package com.laboratorio.springboot25.service;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.dto.ProductoRequest;
import com.laboratorio.springboot25.dto.ProductoResponse;
import com.laboratorio.springboot25.exception.ResourceNotFoundException;
import com.laboratorio.springboot25.model.Producto;
import com.laboratorio.springboot25.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;
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

    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix) {
        return productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }

    @Override
    public List<ProductoResponse> findCategoriaIdOrderByNombreAsc(Integer id) {
        return productoRepository.findByCategoriaIdOrderByNombreAsc(id);
    }

    @Override
    public ProductoResponse createProducto(ProductoRequest request) {
        Optional<ProductoResponse> productoDB = findProductoByNombre(request.getNombre());
        if(productoDB.isPresent()){
            return productoDB.get();
        }
        Optional<CategoriaResponse> categoriaDB =
                categoriaService.findCategoriaById(request.getCategoriaId());
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFoundException("No existe la categoria indicada, " +
                    "no se puede crear el producto");
        }
        Producto producto = new Producto(request);
        Producto productoNuevo = this.productoRepository.save(producto);
        return new ProductoResponse(productoNuevo);
    }
}
