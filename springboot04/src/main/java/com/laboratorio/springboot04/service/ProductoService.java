package com.laboratorio.springboot04.service;

import com.laboratorio.springboot04.modelo.Producto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private List<Producto> productos = new ArrayList<>(
            List.of(
                    new Producto(1, "Mouse", 25.0),
                    new Producto(2, "Teclado", 22.0),
                    new Producto(3, "Monitor", 121.0)
            )
    );

    //    Consulta un producto por su id
    public Producto findById(Integer id){
        return productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
    }

    //    Consulta todos los productos
    public List<Producto> findAll(){
        return productos;
    }

    //    Crea un producto
    public Producto create(Producto producto){
        productos.add(producto);
        return producto;
    }

    //    Actualiza un producto
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
    public String delete(Integer id){
        Producto productoAEliminar = productos.stream()
                .filter(p -> p.getCodigo().equals(id))
                .findAny()
                .orElseThrow();
        productos.remove(productoAEliminar);

        return "Se ha eliminado el producto "+productoAEliminar.getNombre();
    }
}