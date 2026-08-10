package com.laboratorio.springboot12.controller;

import com.laboratorio.springboot12.model.Producto;
import com.laboratorio.springboot12.service.ProductoService;
import com.laboratorio.springboot12.util.exception.InvalidDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ProductoRestController {
    private final ProductoService productoService;


    @GetMapping("/producto/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        Optional<Producto> producto = this.productoService.findById(id);
        if (producto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se ha encontrado el producto: " + id);
        }
        return ResponseEntity.ok(producto.get());
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> findAll() {
        List<Producto> productos = this.productoService.findAll();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/producto")
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        try{
            Producto productoNuevo = this.productoService.create(producto);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{/id}")
                    .buildAndExpand(productoNuevo)
                    .toUri();
            return ResponseEntity.created(location)
                    .body(productoNuevo);
        }catch(InvalidDataException e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error Inesperado");
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @RequestBody Producto producto){
        try{
            Optional<Producto> productoModificar = this.productoService.update(id, producto);
            if(productoModificar.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(productoModificar.get());
        }catch(InvalidDataException e){
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error Inesperado");
        }
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try{
            if(this.productoService.delete(id)){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontro el producto con id: "+id);

        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Error Inesperado");
        }
    }

}
