package com.example.springboot22.unit.repository;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.repository.CategoriaRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
    void findCategoriaByIdTest(){
        Integer id = 2;
        CategoriaResponse categoriaResponse =
                categoriaRepository.findCategoriaById(id).get();
        assertEquals(id, categoriaResponse.getId());
        assertEquals("Categoria 2", categoriaResponse.getNombre());

    }

    @Test
    void findCategoriaByNombreTest(){
        String nombre= "Categoria 3";
        CategoriaResponse categoriaResponse =
                categoriaRepository.findCategoriaByNombre(nombre).get();
        assertEquals(nombre, categoriaResponse.getNombre());
        assertEquals(3, categoriaResponse.getId());
    }

    @Test
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> response =
                categoriaRepository.findAllOrderByNombreAsc();

        assertEquals(3,response.size());
    }

    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix = "TEgo";
        List<CategoriaResponse> response =
                categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(3,response.size());
    }


}
