package com.laboratorio.springboot25.integration.service;

import com.laboratorio.springboot25.dto.CategoriaRequest;
import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.exception.InvalidOperationException;
import com.laboratorio.springboot25.exception.ResourceNotFoundException;
import com.laboratorio.springboot25.service.CategoriaService;
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
class CategoriaServiceTest {
    @Autowired
    private CategoriaService categoriaService;

//    ------------ BUSQUEDAS ------------
    @Test
    @Order(1)
    void findCategoriaByIdTest_CategoryFound() {
        Integer id=2;
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaById(id);
        assertTrue(categoria.isPresent());
        assertEquals(id, categoria.get().getId());
    }
    @Test
    @Order(2)
    void findCategoriaByIdTest_CategoryNotFound(){
        Integer id=999;
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaById(id);
        assertTrue(categoria.isEmpty());
    }
    @Test
    @Order(3)
    void findCategoriaByNameTest_CategoryExists() {
        String nombre = "Categoria 3";
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre(nombre);
        assertTrue(categoria.isPresent());
        assertEquals(nombre, categoria.get().getNombre());
        assertEquals(3,categoria.get().getId());
    }
    @Test
    @Order(4)
    void findCategoriaByNombreTest_CategoriaNotFound(){
        String nombre = "Categoria 999";
        Optional<CategoriaResponse> categoria =
                categoriaService.findCategoriaByNombre(nombre);
        assertTrue(categoria.isEmpty());
    }
    @Test
    @Order(5)
    void findAllOrderByNombreAscTest() {
        List<CategoriaResponse> categorias =
                categoriaService.findAllOrderByNombreAsc();
        assertEquals(3, categorias.size());
    }
    @Test
    @Order(6)
    void findByNombreContainingIgnoreCaseOrderByNombreAscTest() {
          String infix = "IA 1";
          List<CategoriaResponse> categorias =
                  categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix);
          assertEquals(1, categorias.size());
    }
//    ------------- METODOS DE CREACION ------------
    @Test
    @Order(7)
    void createCategoriaTest_CategoryCreated() {
        CategoriaRequest request = new CategoriaRequest("Categoria 4");
        CategoriaResponse categoria = categoriaService.createCategoria(request);
        assertNotNull(categoria);
        assertEquals("Categoria 4", categoria.getNombre());
        assertTrue(categoria.getId()>3);
    }
    @Test
    @Order(8)
    void createCategoriaTest_Exists(){
        CategoriaRequest request = new CategoriaRequest("Categoria 2");
        CategoriaResponse categoria = categoriaService.createCategoria(request);
        assertNotNull(categoria);
        assertEquals("Categoria 2",categoria.getNombre());
        assertEquals(2, categoria.getId());
    }
//    ------------- METODOS DE ACTUALIZACION ------------
    @Test
    @Order(9)
    void updateCategoriaTest_CategoryUpdated() {
        Integer id = 4;
        CategoriaRequest request = new CategoriaRequest("Categoria 4");
        CategoriaResponse categoria = categoriaService.updateCategoria(id, request);
        assertNotNull(categoria);
        assertEquals("Categoria 4", categoria.getNombre());
        assertEquals(4, categoria.getId());
    }
    @Test
    @Order(10)
    void updateCategoriaTest_NotFound(){
        Integer id = 6;
        CategoriaRequest request =
                new CategoriaRequest("Categoria 6");
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> categoriaService.updateCategoria(id, request));
        assertEquals("No se puede efectuar la modificación, " +
                "la categoria no existe", exception.getMessage());
    }
    @Test
    @Order(11)
    void updateCategoriaTest__DUplicateName(){
        Integer id =1;
        CategoriaRequest request =
                new CategoriaRequest("Categoria 2");
        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                () -> categoriaService.updateCategoria(id, request));

        assertEquals("No se puede efectuar la modificación, " +
                "el nombre de la categoria ya existe", exception.getMessage());
    }
//    -------------- METODOS DE ELIMINACION ------------
    @Test
    @Order(12)
    void deleteCategoria_CategoriaDelete(){
        Integer id = 4;
        boolean result = categoriaService.deleteCategoria(id);
        assertTrue(result);
    }
    @Test
    @Order(13)
    void deleteCategoria_CategoryNotFound(){
        Integer id = 6;
        boolean result = categoriaService.deleteCategoria(id);
        assertFalse(result);
    }
    @Test
    @Order(14)
    void deleteCategoria_CategoryHasProducts(){
        Integer id = 2;
        InvalidOperationException exception = assertThrows(
                InvalidOperationException.class,
                () -> categoriaService.deleteCategoria(id));
        assertEquals("No se puede eliminar la categoria, " +
                "la categoria tiene productos asociados", exception.getMessage());
    }
}
