package com.laboratorio.springboot20.repository;

import com.laboratorio.springboot20.dto.CategoriaResponse;
import org.junit.jupiter.api.Assertions;
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
    void  findByCategoriaResponseByIdTest() {
        Integer id= 2;
        CategoriaResponse response=
                this.categoriaRepository.findCategoriaResponseById(id).get();

        Assertions.assertEquals(id,response.getId());
        Assertions.assertEquals("Categoria 2",response.getNombre());
    }

    @Test
    void findCategoriaResponseByNombreTest(){
        String nombre = "Categoria 3";
        CategoriaResponse response =
                this.categoriaRepository.findCategoriaResponseByNombre(nombre).get();

        Assertions.assertEquals(3,response.getId());
        Assertions.assertEquals(nombre,response.getNombre());
    }

    @Test
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> categorias =
            this.categoriaRepository.findAllOrderByNombreAsc();

        Assertions.assertEquals(3,categorias.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix = "TeGo";

        List<CategoriaResponse> response =
            this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
    }
}
