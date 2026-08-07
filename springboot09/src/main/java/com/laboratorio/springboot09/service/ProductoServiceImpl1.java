package com.laboratorio.springboot09.service;

import com.laboratorio.springboot09.modelo.Producto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ProductoServiceImpl1 implements ProductoService{

    private List<Producto> productos = new ArrayList<>(
            List.of(
                new Producto(1, "Mouse", 25.0),
                new Producto(2, "Teclado", 22.0),
                new Producto(3, "Monitor", 121.0)
            )
    );

    @Override
    public Optional<Producto> findById(Integer codigo) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst();
    }

    @Override
    public List<Producto> findAll() {
        return productos;
    }

    @Override
    public Producto create(Producto producto) {
        productos.add(producto);
        return producto;
    }

    @Override
    public Optional<Producto> update(Integer id, Producto producto) {
        Optional<Producto>  productoAModificar = findById(id);
        if(productoAModificar.isEmpty()){
            return Optional.empty();
        }
        productoAModificar.get().setNombre(producto.getNombre());
        productoAModificar.get().setPrecio(producto.getPrecio());
        return productoAModificar;
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Producto> productoAEliminar = findById(id);
        if(productoAEliminar.isEmpty()){
            return false;
        }
        productos.remove(productoAEliminar.get());
        return true;
    }
}
