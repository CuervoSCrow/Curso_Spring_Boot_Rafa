package com.laboratorio.springboot08.service;

import com.laboratorio.springboot08.modelo.Producto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Profile("dev")
@Service
@Order(2)
public class ProductoServiceImpl2 implements ProductoService{

    private List<Producto> productos = new ArrayList<>(
            List.of(
                    new Producto(1, "Mouse", 25.0),
                    new Producto(2, "Teclado", 22.0),
                    new Producto(3, "Monitor", 121.0)
            ));

    @Override
    public Producto findById(Integer id) {
        return productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
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
    public Producto update(Integer id, Producto producto) {
        Producto productoAModificar = productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
        productoAModificar.setNombre(producto.getNombre());
        productoAModificar.setPrecio(producto.getPrecio());
        return productoAModificar;
    }

    @Override
    public String delete(Integer id) {
        Producto productoEliminar = productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
        productos.remove(productoEliminar);
        return "Impl2 Se ha eliminado el producto "+productoEliminar.getNombre();
    }
}
