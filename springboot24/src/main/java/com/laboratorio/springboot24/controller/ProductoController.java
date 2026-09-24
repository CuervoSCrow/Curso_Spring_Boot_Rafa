package com.laboratorio.springboot24.controller;

import com.laboratorio.springboot24.dto.ProductoRequest;
import com.laboratorio.springboot24.dto.ProductoResponse;
import com.laboratorio.springboot24.exception.InvalidOperationException;
import com.laboratorio.springboot24.exception.ResourceNotFoundException;
import com.laboratorio.springboot24.model.Producto;
import com.laboratorio.springboot24.service.ProductoService;
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

//    ------------|| BUSQUEDAS || ------------
    @GetMapping("/find")
    public ResponseEntity<?> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre){

        if(((id==null)&&(nombre==null))||
            ((id!=null) &&( nombre!=null))){
            return ResponseEntity.badRequest().body(
                    "La busqueda debe tener un parametro");
        }
        Optional<ProductoResponse> producto;
        if(id!=null){
            producto = this.productoService.findProductoById(id);
        }else{
            producto = this.productoService.findProductoByName(nombre);
        }
        if(producto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se ha encontrado el producto buscado");
        }
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<ProductoResponse> productos = this.productoService.findAllOrderByNombreAsc();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{infix}")
    public ResponseEntity<?> findByNombreContaining(@PathVariable String infix){
        List<ProductoResponse> productos =
                this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria/{id}")
    public ResponseEntity<?> findByCategoria(@PathVariable Integer id){
        List<ProductoResponse> productos = this.productoService.findByCategoriaIdOrderByNombreAsc(id);
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    // ------------|| CRUD || ------------
    @PostMapping
    public ResponseEntity<?> createProducto(@RequestBody ProductoRequest request){
        try {
            ProductoResponse producto = this.productoService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);
        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id, @RequestBody ProductoRequest request){
        try{
            ProductoResponse producto = this.productoService.updateProduct(id,request);
            return ResponseEntity.ok(producto);
        }catch(ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(InvalidOperationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if(!this.productoService.deleteProduct(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe el producto con id: "+id);
        }
        return ResponseEntity.ok("Se ha eliminado correctamente el " +
                "producto con id: "+id);
    }
}
