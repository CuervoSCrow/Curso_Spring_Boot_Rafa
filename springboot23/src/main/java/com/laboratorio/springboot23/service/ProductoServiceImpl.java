package com.laboratorio.springboot23.service;

import com.laboratorio.springboot23.dto.CategoriaResponse;
import com.laboratorio.springboot23.dto.ProductoRequest;
import com.laboratorio.springboot23.dto.ProductoResponse;
import com.laboratorio.springboot23.exception.InvalidOperationException;
import com.laboratorio.springboot23.exception.ResourceNotFoundException;
import com.laboratorio.springboot23.model.Producto;
import com.laboratorio.springboot23.repository.ProductoRepository;
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
        return productoRepository.findAllOrderNombreAsc();
    }

    @Override
    public List<ProductoResponse> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre);
    }

    @Override
    public List<ProductoResponse> findByCategoriaIdOrderByNombreAsc(Integer id) {
        return productoRepository.findByCategoriaIdOrderByNombreAsc(id);
    }

    @Override
    public ProductoResponse createProducto(ProductoRequest request) {
        Optional<ProductoResponse> productoDB =
                this.findProductoByNombre(request.getNombre());
        if(productoDB.isPresent()){
            return productoDB.get();
        }
        Optional<CategoriaResponse> categoriaDB =
                this.categoriaService.findCategoriaById(request.getCategoriaId());
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFoundException("No existe la categoria indicada," +
                    "no se puede crear el producto");
        }
        Producto producto = new Producto(request);
        Producto productoNuevo = this.productoRepository.save(producto);
        return new ProductoResponse(productoNuevo);
    }

    @Override
    public ProductoResponse updateProducto(Integer id, ProductoRequest request) {
//        Checar si el producto existe
        Optional<ProductoResponse> productoDB = this.findProductoById(id);
        if(productoDB.isEmpty()){
            throw new ResourceNotFoundException("No se puede efectuar la modificacion, " +
                    "El producto no existe.");
        }
        Optional<ProductoResponse> otroProducto =
                this.productoRepository.findProductoByNombre(request.getNombre());
        if(otroProducto.isPresent() &&
            !productoDB.get().getCodigo().equals(otroProducto.get().getCodigo())){
            throw new InvalidOperationException("No se puede modificar el producto por que " +
                    "existe otro con el mismo nombre ");
        }

        Optional<CategoriaResponse> categoriaDB =
                this.categoriaService.findCategoriaById(request.getCategoriaId());
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFoundException("No existe la categoria indicada," +
                    "no se puede modificar el producto");
        }

        Producto producto = new Producto(productoDB.get(),request);
        Producto productoModificado = this.productoRepository.save(producto);
        return new ProductoResponse(productoModificado);
    }

    @Override
    public boolean deleteProducto(Integer id) {
        Optional<ProductoResponse> productoDB = this.findProductoById(id);
        if(productoDB.isEmpty()){
            return false;
        }
        this.productoRepository.deleteById(id);
        return true;
    }

}
