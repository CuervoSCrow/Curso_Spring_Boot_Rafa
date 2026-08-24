package com.laboratorio.springboot19.repository;

import com.laboratorio.springboot19.dto.CategoriaResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void findByCategoriaResponseByIdTest(){
        Integer id = 2;
        CategoriaResponse response =
                this.categoriaRepository.findCategoriaResponseById(id).get();
        assertEquals(id,response.getId());
        assertEquals("Categoria 2",response.getNombre());
    }

    @Test
    void findCategoriaResponseOneByNombreTest(){
        String nombre="Categoria 3";
        CategoriaResponse response =
                this.categoriaRepository.findCategoriaResponseOneByNombre(nombre).get();
        assertEquals(3,response.getId());
        assertEquals(nombre,response.getNombre());
    }

    @Test
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> categorias =
                this.categoriaRepository.findAllOrderByNombreAsc();
        assertEquals(3,categorias.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAsc(){
        String infix="TeGo";

        List<CategoriaResponse> categorias =
                this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }
}
