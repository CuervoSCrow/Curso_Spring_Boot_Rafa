package com.laboratorio.springboot25.integration.controller;

import com.laboratorio.springboot25.dto.CategoriaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindCategoriaById() throws Exception {
        int id=1;


        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoria 1"));

    }
    @Test
    void testFindCategoriaByNombre() throws Exception{
        String nombre = "Categoria 1";

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("nombre",nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }
    @Test
    void testFindCategoriaNotFound() throws Exception{
        int id = 10;

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado la categoria buscada"));
    }
    @Test
    void testFindCategoriaWithoutParams() throws Exception{
        this.mockMvc.perform(get("/api/categorias/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Test
    void testFindCategoriaWithTwoParams() throws Exception{
        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id","1")
                        .param("nombre","Categoria 1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Test
    void testFindAll() throws Exception{

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
    

}
