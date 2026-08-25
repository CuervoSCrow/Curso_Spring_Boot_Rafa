package com.laboratorio.springboot20.service;

import com.laboratorio.springboot20.dto.CategoriaResponse;
import com.laboratorio.springboot20.dto.ProductoRequest;
import com.laboratorio.springboot20.dto.ProductoResponse;
import com.laboratorio.springboot20.exception.ResourceNotFoundException;
import com.laboratorio.springboot20.model.Producto;
import com.laboratorio.springboot20.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{
    private final ProductoRepository productoRepository;
    private final CategoriaService categoriaService;

    @Override
    public Optional<ProductoResponse> findProductoById(Integer id) {
        return this.productoRepository.findProductoById(id);
    }

    @Override
    public Optional<ProductoResponse> findProductoByNombre(String nombre) {
        return this.productoRepository.findByProductoByNombre(nombre);
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
    @Transactional
    public ProductoResponse createProducto(ProductoRequest request) {
        Optional<ProductoResponse> productoDB =
                this.findProductoByNombre(request.getNombre());
        if(productoDB.isPresent()){
            return productoDB.get();
        }
        Optional<CategoriaResponse> categoriaDB =
                this.categoriaService.findCategoriaResponseById(request.getCategoriaId());
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFoundException("No Existe la Categoria Indicada no se puede" +
                    " crear el producto");
        }
        Producto producto = new Producto(request);
        Producto productoNuevo = this.productoRepository.save(producto);
        return new ProductoResponse(productoNuevo);

    }

    @Override
    @Transactional
    public ProductoResponse updateProducto(Integer id, ProductoRequest request) {
//       Checa si el producto existe
        Optional<ProductoResponse> productoDB =
                this.findProductoById(id);
        if(productoDB.isEmpty()){
            throw new ResourceNotFoundException("No se puede efectuar la modificación, " +
                    "El producto no existe.");
        }
//
        Optional<ProductoResponse> otroProducto =
                this.productoRepository.findByProductoByNombre(request.getNombre());
        if(otroProducto.isPresent() && !productoDB.get().getCodigo().equals(otroProducto.get().getCodigo())){
            throw new ResourceNotFoundException("No se puede modificar el producto, " +
                    "Ya que existe otro con el mismo nomrbe.");
        }

        Optional<CategoriaResponse> categoriaDB =
                this.categoriaService.findCategoriaResponseById(request.getCategoriaId());
        if(categoriaDB.isEmpty()){
            throw new ResourceNotFoundException("No Existe la Categoria Indicada no se puede" +
                    " modificar el producto");
        }

        Producto producto = new Producto(productoDB.get(), request);
        Producto productoModificado = this.productoRepository.save(producto);

        return new ProductoResponse(productoModificado) ;
    }

    @Override
    @Transactional
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
