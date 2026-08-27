package com.laboratorio.springboot21.service;

import com.laboratorio.springboot21.dto.ProductoRequest;
import com.laboratorio.springboot21.dto.ProductoResponse;
import com.laboratorio.springboot21.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllOrderByNombreAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
    List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer categoriaId);
    ProductoResponse createProducto(ProductoRequest request);
    ProductoResponse updateProducto(Integer id,ProductoRequest request);
    boolean deleteProducto(Integer id);
}
