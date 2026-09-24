package com.laboratorio.springboot24.unit.controller;

import com.laboratorio.springboot24.controller.CategoriaController;
import com.laboratorio.springboot24.dto.CategoriaResponse;
import com.laboratorio.springboot24.service.CategoriaService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoriaController.class)
public class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void testCategoriaById() throws Exception{
        int id = 1;
        CategoriaResponse categoria = new CategoriaResponse(id, "Categoria 1");
        when(categoriaService.findCategoriaById(id)).thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                    .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoria 1"));
    }
}
