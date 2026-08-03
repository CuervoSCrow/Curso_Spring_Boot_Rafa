package com.laboratorio.springboot06.controller;

import com.laboratorio.springboot06.modelo.Producto;
import com.laboratorio.springboot06.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductoRestController {

//    Primera forma de inyeccion de dependencias
//    @Autowired
//    private ProductoService productoService;

//    Segunda forma de inyeccion de dependencias.
//    @Autowired
//    public void setProductoService(ProductoService productoService) {
//        this.productoService = productoService;
//    }

//    Tercera forma de inyeccion de dependencias y la mas recomendada.
//    private final ProductoService productoService;
//    public ProductoRestController(ProductoService productoService) {
//        this.productoService = productoService;
//    }

//    La cuarta forma de inyeccion de dependencia se utiliza con lombok
    private final ProductoService productoService;

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
