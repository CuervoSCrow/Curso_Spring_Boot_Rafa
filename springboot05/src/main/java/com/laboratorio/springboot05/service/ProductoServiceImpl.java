package com.laboratorio.springboot05.service;

import com.laboratorio.springboot05.modelo.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class ProductoServiceImpl implements ProductoService {
    private List<Producto> productos = new ArrayList<>(
            List.of(
                    new Producto(1, "Mouse", 25.0),
                    new Producto(2, "Teclado", 22.0),
                    new Producto(3, "Monitor", 121.0)
            )
    );

    //    Consulta un producto por su id
    @Override
    public Producto findById(Integer id){
        return productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
    }

    //    Consulta todos los productos
    @Override
    public List<Producto> findAll(){
        return productos;
    }

    //    Crea un producto
    @Override
    public Producto create(Producto producto){
        productos.add(producto);
        return producto;
    }

    //    Actualiza un producto
    @Override
    public Producto update(Integer id,
                           Producto producto){
        Producto productoAModificar = productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
        productoAModificar.setNombre(producto.getNombre());
        productoAModificar.setPrecio(producto.getPrecio());
        return productoAModificar;
    }

    //    Elimina un producto
    @Override
    public String delete(Integer id){
        Producto productoAEliminar = productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
        productos.remove(productoAEliminar);

        return "Se ha eliminado el producto "+productoAEliminar.getNombre();
    }
}
