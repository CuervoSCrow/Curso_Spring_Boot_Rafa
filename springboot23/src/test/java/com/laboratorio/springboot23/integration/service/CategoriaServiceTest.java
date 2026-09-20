package com.laboratorio.springboot23.integration.service;

import com.laboratorio.springboot23.dto.CategoriaRequest;
import com.laboratorio.springboot23.dto.CategoriaResponse;
import com.laboratorio.springboot23.service.CategoriaService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaServiceTest {
//    ----------------------
//    |  VARIABLES DE TEST |
//    ----------------------
    @Autowired
    private CategoriaService categoriaService;

//    BUSQUEDAS

    @Test
    @Order(1)
    void testFindCategoriaById_CategoriaExists() {
        Integer id = 2;
        Optional<CategoriaResponse> categoria=
                categoriaService.findCategoriaById(id);
        assertTrue(categoria.isPresent());
        assertEquals("Categoria 2",categoria.get().getNombre());
    }
    
    @Test
    @Order(2)
    void testFindCategoriaById_CategoriaNotFound(){
        Optional<CategoriaResponse> categoria=
                categoriaService.findCategoriaById(100);
        assertTrue(categoria.isEmpty());
    }

    @Test
    @Order(3)
    void findCategoriaByNombre_CategoriaExists() {
        String nombre="Categoria 3";

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaByNombre(nombre);
        assertTrue(categoria.isPresent());
        assertEquals(3,categoria.get().getId());
    }

    @Test
    @Order(4)
    void findCategoriaByNombre_CategoriaNotFound(){
        String nombre = "Categoria 5";

        Optional<CategoriaResponse> categoria =
                this.categoriaService.findCategoriaByNombre(nombre);

        assertTrue(categoria.isEmpty());
    }

    @Test
    @Order(5)
    void findAllOrderByNombreAscTest() {
        List<CategoriaResponse> categorias =
                categoriaService.findAllOrderByNombreAsc();

        assertEquals(3,categorias.size());
    }

    @Test
    @Order(6)
    void indByNombreContainingIgnoreCaseOrderByNombreAscTest(){
        String infix = "IA 1";
        List<CategoriaResponse> categorias =
                categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
        assertEquals(1,categorias.size());
    }

//    CREACION

    @Test
    @Order(7)
    void createCategoriaTest_CategoriaCreated(){
        CategoriaRequest request = new CategoriaRequest("Categoria 4");

        CategoriaResponse categoria =
                categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals("Categoria 4",categoria.getNombre());
        assertEquals(4,categoria.getId());
    }

    @Test
    @Order(8)
    void createCategoriaTest_ReturnExsisting(){
        CategoriaRequest request = new CategoriaRequest("Categoria 2");
        CategoriaResponse categoria =
                categoriaService.createCategoria(request);

        assertNotNull(categoria);
        assertEquals("Categoria 2", categoria.getNombre());
        assertEquals(2, categoria.getId());
    }





    
}
