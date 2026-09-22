package com.laboratorio.springboot23.controller;

import com.laboratorio.springboot23.dto.CategoriaRequest;
import com.laboratorio.springboot23.dto.CategoriaResponse;
import com.laboratorio.springboot23.exception.InvalidOperationException;
import com.laboratorio.springboot23.exception.ResourceNotFoundException;
import com.laboratorio.springboot23.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/find")
    public ResponseEntity<?> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre
    ){
        if(((id==1)&&(nombre==null))||((id!=null)&&(nombre!=null))){
            return ResponseEntity.badRequest().body("La busqueda " +
                    "debe tener solo un parametro");
        }
        Optional<CategoriaResponse> categoria;
        if(id!=null){
            categoria = categoriaService.findCategoriaById(id);
        }else{
            categoria = categoriaService.findCategoriaByNombre(nombre);
        }
        if(categoria.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se ha encontrado la categoria buscada");
        }
        return ResponseEntity.ok(categoria.get());
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<CategoriaResponse> categorias =
                this.categoriaService.findAllOrderByNombreAsc();
        if(categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{infix}")
    public ResponseEntity<?> findByNombreContaning(
            @PathVariable String infix){
        List<CategoriaResponse> categorias =
                this.categoriaService
                    .findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        if(categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> create(
            @RequestBody CategoriaRequest request){
        CategoriaResponse categoria =
                this.categoriaService.createCategoria(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody CategoriaRequest request){

        try{
            CategoriaResponse categoria = this.categoriaService.updateCategoria(id, request);
            return ResponseEntity.ok(categoria);
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch(InvalidOperationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }
    @DeleteMapping("/id")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        try{
            if(this.categoriaService.deleteCategoria(id)){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No existe una categoria con id: "+id);
            }
            return ResponseEntity.ok("Se ha eliminado correctamente la categoria con id: "+id);
        }catch(InvalidOperationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(Exception e){
            return ResponseEntity.internalServerError().body("Ha ocurrido un error inesperado");
        }
    }
}
