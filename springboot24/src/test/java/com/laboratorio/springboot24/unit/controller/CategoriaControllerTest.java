package com.laboratorio.springboot24.unit.controller;

import com.laboratorio.springboot24.controller.CategoriaController;
import com.laboratorio.springboot24.dto.CategoriaRequest;
import com.laboratorio.springboot24.dto.CategoriaResponse;
import com.laboratorio.springboot24.service.CategoriaService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(controllers = CategoriaController.class)
public class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

//    --------------||| Test Find |||--------------
    @Test
    void testCategoriaById() throws Exception{
        int id = 1;
        CategoriaResponse categoria = new CategoriaResponse(id, "Categoria 1");
        when(categoriaService.findCategoriaById(id))
                .thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                    .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoria 1"));
    }
    @Test
    void testCategoriaByName() throws Exception{
        String nombre = "Producto 1";
        CategoriaResponse categoria = new CategoriaResponse(1, nombre);
        when(categoriaService.findCategoriaByNombre(nombre))
                .thenReturn(Optional.of(categoria));

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("nombre",nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value(nombre));
    }
    @Test
    void testFindCategoriaNotFound() throws Exception{
        int id =1;
        when(this.categoriaService.findCategoriaById(id))
                .thenReturn(Optional.empty());

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
    void testFindCategoiraWithTwoParams() throws Exception{
        this.mockMvc.perform(get("/api/categorias/find")
                            .param("id", String.valueOf(1))
                            .param("nombre", "Producto 1"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("La busqueda debe tener un parametro"));
    }
//    --------------||| Test FindAll |||--------------
    @Test
    void testFindAll() throws Exception{
        List<CategoriaResponse> categorias = List.of(
                new CategoriaResponse(1,"Categoria 1"),
                new CategoriaResponse(2,"Categoria 2"),
                new CategoriaResponse(3,"Categoria 3")
        );
        when(this.categoriaService.findAllOrderByNombreAsc())
                .thenReturn(categorias);
        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void testFindAllNoContent() throws Exception{

        when(this.categoriaService.findAllOrderByNombreAsc())
                .thenReturn(List.of());
        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isNoContent());
    }
//    --------------||| Test FindByNombreContaining |||--------------
    @Test
    void testFindByNombreContaining() throws Exception{
        String infix="TeGo";
        List<CategoriaResponse> categorias = List.of(
                new CategoriaResponse(1,"Categoria 1"),
                new CategoriaResponse(2,"Categoria 2"),
                new CategoriaResponse(3,"Categoria 3")
        );
        when(this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(categorias);

        this.mockMvc.perform(get("/api/categorias/"+infix)                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void testFindByNombreContainingNoContent() throws Exception{
        String infix="TeGo";
        when(this.categoriaService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(List.of());
        this.mockMvc.perform(get("/api/categorias/"+infix))
                .andExpect(status().isNoContent());
    }
//    --------------||| Test Create |||--------------
    @Test
    void testCreate() throws Exception{
        CategoriaRequest request = new CategoriaRequest("Categoria Nueva");
        CategoriaResponse categoria = new CategoriaResponse(1,"Categoria Nueva");

        when(this.categoriaService.createCategoria(any(CategoriaRequest.class)))
                .thenReturn(categoria);
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Categoria Nueva"));
    }


}
