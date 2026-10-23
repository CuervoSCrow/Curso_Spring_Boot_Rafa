package com.laboratorio.springboot27.controller;

import com.laboratorio.springboot27.dto.ProductoResponse;
import com.laboratorio.springboot27.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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



}
