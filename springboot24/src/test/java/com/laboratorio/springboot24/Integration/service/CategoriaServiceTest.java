package com.laboratorio.springboot24.Integration.service;

import com.laboratorio.springboot24.dto.CategoriaRequest;
import com.laboratorio.springboot24.dto.CategoriaResponse;
import com.laboratorio.springboot24.service.CategoriaService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaServiceTest {
    @Autowired
    private CategoriaService categoriaService;

    @Test
    @Order(1)
    void findCategoriaById_CategoryFoundTest(){
        Integer id = 2;
        Optional<CategoriaResponse> categoria = categoriaService.findCategoriaById(id);
        assertTrue(categoria.isPresent());
        assertEquals("Categoria 2", categoria.get().getNombre());
    }
    @Test
    @Order(2)
    void findCategoriaById_CategoryNotFoundTest(){
        Integer id = 10;
        Optional<CategoriaResponse> categoria = categoriaService.findCategoriaById(id);
        assertTrue(categoria.isEmpty());
    }
    @Test
    @Order(3)
    void findCategoriaByNombre_CategoriaExistsTest(){
        String nombre = "Categoria 3";
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre(nombre);
        assertTrue(categoria.isPresent());
        assertEquals(nombre, categoria.get().getNombre());
        assertEquals(3, categoria.get().getId());
    }
    @Test
    @Order(4)
    void findCategoriaByNombre_CategoriaNotFoundTest(){
        String nombre = "Categoria 10";
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre(nombre);
        assertTrue(categoria.isEmpty());
    }
    @Test
    @Order(5)
    void findAllOrderByNombreAscTest(){
        List<CategoriaResponse> categorias =
                categoriaService.findAllOrderByNombreAsc();
        assertEquals(3, categorias.size());
    }
    @Test
    @Order(6)
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix = "IA 1";
        List<CategoriaResponse> categorias =
                categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(1, categorias.size());
    }
    @Test
    @Order(7)
    void createCategoria_CategoryCreated(){
        CategoriaRequest request = new CategoriaRequest("Categoria 4");
        CategoriaResponse categoria = categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals("Categoria 4", categoria.getNombre());
        assertEquals(4,categoria.getId());
    }

}
