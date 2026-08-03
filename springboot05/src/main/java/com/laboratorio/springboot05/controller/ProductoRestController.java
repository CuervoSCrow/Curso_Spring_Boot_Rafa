package com.laboratorio.springboot05.controller;


import com.laboratorio.springboot05.modelo.Producto;
import com.laboratorio.springboot05.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    //    Consulta un producto por su id
    @GetMapping(value="/productos/{id}")
    public Producto findById(@PathVariable Integer id){
        return this.productoService.findById(id);
    }

    //    Consulta todos los productos
    @GetMapping(value="/productos")
    public List<Producto> findAll(){
        return this.productoService.findAll();
    }

    //    Crea un producto
    @PostMapping(value="/productos")
    public Producto create(@RequestBody Producto producto){
        return this.productoService.create(producto);
    }

    //    Actualiza un producto
    @PutMapping(value = "/productos/{id}")
    public Producto update(@PathVariable Integer id,
                           @RequestBody Producto producto){
        return this.productoService.update(id, producto);
    }

    //    Elimina un producto
    @DeleteMapping(value = "/productos/{id}")
    public String delete(@PathVariable Integer id){
        return this.productoService.delete(id);
    }
}
