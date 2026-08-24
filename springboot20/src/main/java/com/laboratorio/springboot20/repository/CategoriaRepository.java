package com.laboratorio.springboot20.repository;

import com.laboratorio.springboot20.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends
        JpaRepository<Categoria,Integer> {


}
