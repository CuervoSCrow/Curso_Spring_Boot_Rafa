package com.laboratorio.springboot17.service;

import com.laboratorio.springboot17.model.Producto;
import com.laboratorio.springboot17.repository.ProductoRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;

    @Override
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto create(Producto producto) {
        if(producto.getPrecio()>3000){
            throw new RuntimeException("El precio no puede ser mayor a 3000");
        }
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> update(Integer id, Producto producto) {
        if(producto.getPrecio()>3000){
            throw new RuntimeException("El precio no puede ser mayor a 3000");
        }
        Optional<Producto> productoModificar = productoRepository.findById(id);
        if(productoModificar.isEmpty()){
            return Optional.empty();
        }
        productoModificar.get().setNombre(producto.getNombre());
        productoModificar.get().setPrecio(producto.getPrecio());
        return Optional.of(this.productoRepository.save(productoModificar.get()));
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Producto> productoEliminar = findById(id);
        if(productoEliminar.isEmpty()){
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }
}
