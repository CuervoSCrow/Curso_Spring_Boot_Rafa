package com.laboratorio.springboot25.unit.repository;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void testFindCategoriaById() {
        Integer id = 2;
        CategoriaResponse response =
                categoriaRepository.findCategoriaById(id).get();
        assertEquals(id, response.getId());
    }
    @Test
    void testFindCategoriaByNombre() {
        String nombre="Categoria 3";
        CategoriaResponse response =
                categoriaRepository.findCategoriaByNombre(nombre).get();
        assertEquals(nombre, response.getNombre());
        assertEquals(3, response.getId());
    }
    @Test
    void testFindAllOrderByNombreAsc() {
        List<CategoriaResponse> response =
            this.categoriaRepository.findAllOrderByNombreAsc();
        assertEquals(3,response.size());
    }
    @Test
    void testFindByNombreContainingIgnoreCaseOrderByNombreAsc() {
        String infix="TeGO";
        List<CategoriaResponse> response =
            this.categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(3,response.size());
    }

}
