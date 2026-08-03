package com.laboratorio.springboot07.service;

import com.laboratorio.springboot07.modelo.Producto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Primary  // Primera forma solo coloca primary
@Service("implementacion1")
@ConditionalOnProperty(name = "implementacion.producto.service",
        havingValue = "implementacion_1",
        matchIfMissing = true)
public class ProductoServiceImpl1 implements ProductoService{

    private List<Producto> productos = new ArrayList<>(

        List.of(
                new Producto(1, "Mouse", 25.0),
                new Producto(2, "Teclado", 22.0),
                new Producto(3, "Monitor", 121.0)
        ));

//    Consulta un producto por su id
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
    public Producto update(Integer id,
                           Producto producto) {
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
        Producto productoAEliminar = productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
        productos.remove(productoAEliminar);
        return "Impl1 Se ha eliminado el producto "+productoAEliminar.getNombre();
    }
}
