package com.laboratorio.springboot26.service;

import com.laboratorio.springboot26.dto.CategoriaResponse;
import com.laboratorio.springboot26.dto.ProductoRequest;
import com.laboratorio.springboot26.dto.ProductoResponse;
import com.laboratorio.springboot26.exception.InvalidOperationException;
import com.laboratorio.springboot26.exception.ResourceNotFoundException;
import com.laboratorio.springboot26.model.Producto;
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
    private final CategoriaService categoriaService;

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
    @Override
    public ProductoResponse createProducto(ProductoRequest request) {
        Optional<ProductoResponse> productoDB = this.findProductoByNombre(request.getNombre());
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
    @Override
    public ProductoResponse updateProducto(Integer id, ProductoRequest request) {
        Optional<ProductoResponse> productoDB = this.findProductoById(id);
        if (productoDB.isEmpty()) {
            throw new ResourceNotFoundException("No se puede efectuar la modificacion, " +
                    "el producto no existe.");
        }
        Optional<ProductoResponse> otroProducto =
                this.findProductoByNombre(request.getNombre());
        if (otroProducto.isPresent() &&
                !productoDB.get().getCodigo().equals(otroProducto.get().getCodigo())) {
            throw new InvalidOperationException("No se puede modificar el producto, " +
                    "existe otro con el mismo nombre.");
        }
        Optional<CategoriaResponse> categoriaDB =
                categoriaService.findCategoriaById(request.getCategoriaId());
        if (categoriaDB.isEmpty()) {
            throw new ResourceNotFoundException("No existe la categoria indicada, " +
                    "no se puede modificar el producto");
        }
        Producto producto = new Producto(productoDB.get(), request);
        Producto productoModificar = this.productoRepository.save(producto);
        return new ProductoResponse(productoModificar);
    }
    @Override
    public boolean deleteProducto(Integer id) {
        Optional<ProductoResponse> productoDB =
                this.findProductoById(id);
        if(productoDB.isEmpty()){
            return false;
        }
        this.productoRepository.deleteById(id);
        return true;
    }


}
