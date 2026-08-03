package com.laboratorio.springboot07.controller;

import com.laboratorio.springboot07.modelo.Producto;
import com.laboratorio.springboot07.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class ProductoRestController {
    private final ProductoService productoService;

//    Para Inyección de dependencias multiples se quita el RequiredArgsConstructor
//    y se coloca @Qualifier con el nombre de cada implementación @Service("implementacion1")

//    public ProductoRestController(@Qualifier("implementacion2") ProductoService productoService) {
//        this.productoService = productoService;
//    }

//    Para la tercera forma de inyección de dependencias multiples
//    Modificas el archivo application.properties = implementacion.producto.service=implementacion_1
//    y en los archivos de implementacion tienen lo siguiente
//    @Service("implementacion2")
//    @ConditionalOnProperty(name = "implementacion.producto.service",
//                          havingValue = "implementacion_2")
//    public class ProductoServiceImpl2 implements ProductoService{
//    @Service("implementacion1")
//    @ConditionalOnProperty(name = "implementacion.producto.service",
//        havingValue = "implementacion_1",
//        matchIfMissing = true)
//     public class ProductoServiceImpl1 implements ProductoService{

    @GetMapping("/productos")
    public List<Producto> findAll() {
        return productoService.findAll();
    }

    @GetMapping("/productos/{id}")
    public Producto findById(@PathVariable Integer id) {
        return productoService.findById(id);
    }

    @PostMapping("/productos")
    public Producto create(@RequestBody Producto producto) {
        return productoService.create(producto);
    }

    @PutMapping("/productos/{id}")
    public Producto update(@PathVariable Integer id,
                           @RequestBody Producto producto) {
        return productoService.update(id, producto);
    }

    @DeleteMapping("/productos/{id}")
    public String delete(@PathVariable Integer id) {
        return productoService.delete(id);
    }
}
