package com.laboratorio.springboot21.repository;

import com.laboratorio.springboot21.dto.CategoriaResponse;
import com.laboratorio.springboot21.model.Categoria;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void findCategoriaByIdTest() {
        Integer id = 2;
        CategoriaResponse response =
                categoriaRepository.findCategoriaById(id).get();

        Assertions.assertEquals(id,response.getId());
        Assertions.assertEquals("Categoria 2",response.getNombre());

    }

    @Test
    void findCategoriaByNombreTest(){
        String nombre = "Categoria 3";
        CategoriaResponse response =
                categoriaRepository.findCategoriaByNombre(nombre).get();

        Assertions.assertEquals(nombre,response.getNombre());
        Assertions.assertEquals(3,response.getId());
    }

    @Test
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> response =
                categoriaRepository.findAllOrderByNombreAsc();

        Assertions.assertEquals(3,response.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix= "TegO";
        List<CategoriaResponse> response =
                categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);

        Assertions.assertEquals(3,response.size());
    }

}
