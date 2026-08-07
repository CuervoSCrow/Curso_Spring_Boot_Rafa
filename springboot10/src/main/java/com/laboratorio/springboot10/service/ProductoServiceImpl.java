package com.laboratorio.springboot10.service;

import com.laboratorio.springboot10.modelo.Producto;
import com.laboratorio.springboot10.util.exception.InvalidDataException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService{

    private List<Producto> productos = new ArrayList<>(
        List.of(
                new Producto(1, "Mouse", 25.0),
                new Producto(2, "Teclado", 22.0),
                new Producto(3, "Monitor", 121.0)
        )
    );

    @Override
    public Optional<Producto> findById(Integer id) {
        return productos.stream()
            .filter(p -> p.getCodigo().equals(id))
            .findFirst();
    }

    @Override
    public List<Producto> findAll() {
        return productos;
    }

    @Override
    public Producto create(Producto producto) {
        if(producto.getPrecio()>3000){
            throw new InvalidDataException("Precio invalido");
        }
        productos.add(producto);
        return producto;
    }

    @Override
    public Optional<Producto> update(Integer id, Producto producto) {
        if(producto.getPrecio()>3000){
            throw new InvalidDataException("Precio invalido");
        }
        Optional<Producto> productoAModificar = findById(id);
        if(productoAModificar.isEmpty()){
            return Optional.empty();
        }
        productoAModificar.get().setNombre(producto.getNombre());
        productoAModificar.get().setPrecio(producto.getPrecio());
        return productoAModificar;
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Producto> productoEliminar = findById(id);
        if(productoEliminar.isEmpty()){
            return false;
        }
        productos.remove(productoEliminar.get());
        return true;
    }
}
