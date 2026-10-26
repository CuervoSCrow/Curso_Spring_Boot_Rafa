package com.laboratorio.springboot27.controller;

import com.laboratorio.springboot27.dto.ProductoResponse;
import com.laboratorio.springboot27.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/find")
    public ResponseEntity<?> findProducto(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre){
        if(((id==null)&&(nombre==null)||
                (id!=null&&nombre!=null))){
            return ResponseEntity.badRequest().body(
                    "La busqueda debe tener un parametro");
        }
        Optional<ProductoResponse> producto;
        if(id!=null){
            producto = this.productoService.findProductoById(id);
        }else{
            producto = this.productoService.findProductoByNombre(nombre);
        }
        if(producto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se ha encontrado el producto buscado.");
        }
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?>findAll(){
        List<ProductoResponse> productos = this.productoService.findAllProducto();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("{infix}")
    public ResponseEntity<?>findByNombreContaining(
            @PathVariable String infix){
        List<ProductoResponse> productos =
                this.productoService.findByNombreContainingIgnoreCase(infix);
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> findByCategoria(
            @PathVariable Integer id){
        List<ProductoResponse> productos =
            this.productoService.findByCategoriaId(id);
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }



}
