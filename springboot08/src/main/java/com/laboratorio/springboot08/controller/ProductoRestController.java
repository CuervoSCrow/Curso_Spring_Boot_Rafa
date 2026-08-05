package com.laboratorio.springboot08.controller;

import com.laboratorio.springboot08.modelo.Producto;
import com.laboratorio.springboot08.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequiredArgsConstructor
//public class ProductoRestController {
//    private final ProductoService productoService;
//
//    @GetMapping("/productos")
//    public List<Producto> findAll() {
//        return productoService.findAll();
//    }
//
//    @GetMapping("/productos/{id}")
//    public Producto findById(@PathVariable Integer id) {
//        return productoService.findById(id);
//    }
//
//    @PostMapping("/productos")
//    public Producto create(@RequestBody Producto producto) {
//        return productoService.create(producto);
//    }
//
//    @PutMapping("/productos/{id}")
//    public Producto update(@PathVariable Integer id,
//                           @RequestBody Producto producto) {
//        return productoService.update(id, producto);
//    }
//
//    @DeleteMapping("/productos/{id}")
//    public String delete(@PathVariable Integer id) {
//        return productoService.delete(id);
//    }
//}

@RestController
@RequiredArgsConstructor
public class ProductoRestController {
    private final List<ProductoService> productoService;

    private final int pos=0;

    @GetMapping(value = "/productos/{id}")
    public Producto findById(@PathVariable Integer id) {
        return this.productoService.get(pos).findById(id);
    }

    @GetMapping(value="/productos")
    public List<Producto> findAll() {
        return this.productoService.get(0).findAll();
    }

    @PostMapping(value="/productos")
    public Producto create(@RequestBody Producto producto) {
        return this.productoService.get(pos).create(producto);
    }

    @PutMapping(value="/productos/{id}")
    public Producto update(@PathVariable Integer id, @RequestBody Producto producto) {
        return this.productoService.get(pos).update(id, producto);
    }

    @DeleteMapping(value="/productos/{id}")
    public String delete(@PathVariable Integer id) {
        return this.productoService.get(pos).delete(id);
    }
}

