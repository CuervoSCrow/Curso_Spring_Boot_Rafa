package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.ProductoRequest;
import com.laboratorio.springboot26.dto.ProductoResponse;


import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Optional<ProductoResponse> findProductoById(Integer id);
    Optional<ProductoResponse> findProductoByNombre(String nombre);
    List<ProductoResponse> findAllProductosAsc();
    List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String infix);
    List<ProductoResponse> findByCategoriaId(Integer categoriaId);
    ProductoResponse createProducto(ProductoRequest request);
    ProductoResponse updateProducto(Integer id, ProductoRequest request);
    boolean deleteProducto(Integer id);

}
