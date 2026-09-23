package com.laboratorio.springboot24.unit.repository;

import com.laboratorio.springboot24.dto.CategoriaResponse;
import com.laboratorio.springboot24.repository.CategoriaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void testFindCaregoriaByIdTest() {
        Integer id=2;
        CategoriaResponse response =
                categoriaRepository.findCategoriaById(id).get();
        assertEquals(id,response.getId());
    }
    @Test
    void findCategoriaByNombreTest(){
        String nombre ="Categoria 3";
        CategoriaResponse response =
                categoriaRepository.findCategoriaByNombre(nombre).get();
        assertEquals(nombre,response.getNombre());
        assertEquals(3,response.getId());
    }
    @Test
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> response =
                categoriaRepository.findAllOrderByNombreAsc();
        assertEquals(3,response.size());
    }
    @Test
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix="TeGo";
        List<CategoriaResponse> response =
                categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(3,response.size());
    }

}
