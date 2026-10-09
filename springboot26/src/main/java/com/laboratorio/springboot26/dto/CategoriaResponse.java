package com.laboratorio.springboot26.dto;

import com.laboratorio.springboot26.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CategoriaResponse {
    private Integer id;
    private String nombre;

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }
}
