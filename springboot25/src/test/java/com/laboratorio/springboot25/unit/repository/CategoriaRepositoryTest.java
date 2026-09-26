package com.laboratorio.springboot25.unit.repository;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.repository.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class CategoriaRepositoryTest {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void testFindCategoriaById() {
        Integer id = 2;
        CategoriaResponse response =
                categoriaRepository.findCategoriaById(id).get();
        assertEquals(id, response.getId());
    }
}
