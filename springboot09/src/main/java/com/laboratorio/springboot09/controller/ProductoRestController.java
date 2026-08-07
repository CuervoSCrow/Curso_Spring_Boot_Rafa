package com.laboratorio.springboot09.controller;

import com.laboratorio.springboot09.modelo.Producto;
import com.laboratorio.springboot09.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductoRestController {
    private final ProductoService productoService;


    @GetMapping("/productos/{id}")
    public ResponseEntity<?> getProductoById(@PathVariable Integer id) {
        Optional<Producto> producto = this.productoService.findById(id);
        if(producto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No se encontro el producto "+id);
        }
        return ResponseEntity.ok(producto.get());
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> findAll() {
        return ResponseEntity.ok(this.productoService.findAll());
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        Producto productoNuevo = this.productoService.create(producto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Codigo-Producto",Integer.toString(productoNuevo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(productoNuevo);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                           @RequestBody Producto producto) {
        Optional<Producto> productoModificado = this.productoService.update(id,producto);
        if(productoModificado.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoModificado.get());
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if(this.productoService.delete(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se encontro el producto con id: "+id);

    }
}
