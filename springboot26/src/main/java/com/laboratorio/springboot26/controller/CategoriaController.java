package com.laboratorio.springboot26.controller;

import com.laboratorio.springboot26.dto.CategoriaResponse;
import com.laboratorio.springboot26.service.CategoriaService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping("/find")
    public ResponseEntity<?> findCategoria(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String nombre){
        if(((id==null)&&(nombre==null))||
                ((id!=null)&&(nombre!=null))){
            return ResponseEntity.badRequest()
                    .body("La busqueda debe tener un parametro");
        }
        Optional<CategoriaResponse> categoria;
        if(id!=null){
            categoria = categoriaService.findCategoriaById(id);
        }else{
            categoria = categoriaService.findCategoriaByNombre(nombre);
        }
        if(categoria.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "No se ha encontrado la categoria buscada");
        }
        return ResponseEntity.ok(categoria.get());
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<CategoriaResponse> categorias =
                categoriaService.findAllCategoria();
        if(categorias.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    

}
