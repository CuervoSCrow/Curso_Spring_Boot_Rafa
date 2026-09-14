package com.example.springboot22.unit.repository;

import com.example.springboot22.dto.CategoriaResponse;
import com.example.springboot22.repository.CategoriaRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

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
}
