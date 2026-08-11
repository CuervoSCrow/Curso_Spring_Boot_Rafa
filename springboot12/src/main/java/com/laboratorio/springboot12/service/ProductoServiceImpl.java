package com.laboratorio.springboot12.service;

import com.laboratorio.springboot12.model.Producto;
import com.laboratorio.springboot12.repository.ProductoRepository;
import com.laboratorio.springboot12.util.exception.InvalidDataException;
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
        return this.productoRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return this.productoRepository.findAll();
    }

    @Override
    public Producto create(Producto producto) {
        if(producto.getPrecio()>3000){
            throw new InvalidDataException("El precio debe ser menor a 3000");
        }
        return this.productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> update(Integer id, Producto producto) {
        if(producto.getPrecio()>3000){
            throw new InvalidDataException("Precio Invalido");
        }
        Optional<Producto> productoModificar = findById(id);
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
        this.productoRepository.deleteById(id);
        return true;
    }
}
