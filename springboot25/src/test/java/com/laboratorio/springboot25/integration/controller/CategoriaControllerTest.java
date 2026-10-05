package com.laboratorio.springboot25.integration.controller;

import com.laboratorio.springboot25.dto.CategoriaRequest;
import com.laboratorio.springboot25.dto.CategoriaResponse;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.greaterThan;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static Integer createId;

    @Test
    @Order(1)
    void testFindCategoriaById() throws Exception {
        int id=1;


        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Categoria 1"));

    }
    @Order(2)
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
    @Order(3)
    @Test
    void testFindCategoriaNotFound() throws Exception{
        int id = 10;

        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado la categoria buscada"));
    }
    @Order(4)
    @Test
    void testFindCategoriaWithoutParams() throws Exception{
        this.mockMvc.perform(get("/api/categorias/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Order(5)
    @Test
    void testFindCategoriaWithTwoParams() throws Exception{
        this.mockMvc.perform(get("/api/categorias/find")
                        .param("id","1")
                        .param("nombre","Categoria 1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Order(6)
    @Test
    void testFindAll() throws Exception{

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
    @Order(7)
    @Test
    void testFindByNombreContaining() throws Exception{
        String infix="Tego";

        this.mockMvc.perform(get("/api/categorias/"+infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
    @Order(8)
    @Test
    void testFindByNombreContainingNotContent() throws  Exception{
        String infix="texto";

        this.mockMvc.perform(get("/api/categorias/"+infix))
                .andExpect(status().isNoContent());

    }
    @Order(9)
    @Test
    void testCreate() throws Exception{
        CategoriaRequest request = new CategoriaRequest("Categoria nueva");
        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(greaterThan(3)))
                .andExpect(jsonPath("$.nombre").value("Categoria nueva"))
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        CategoriaResponse categoria = objectMapper.readValue(responseBody, CategoriaResponse.class);
        this.createId = categoria.getId();
    }
    @Order(10)
    @Test
    void testCreate_ReturnExisting() throws Exception{
        CategoriaRequest request = new CategoriaRequest("Categoria 2");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Categoria 2"))
                .andReturn();
    }
    @Order(11)
    @Test
    void testUpdate() throws Exception{
        int id=createId;
        CategoriaRequest request = new CategoriaRequest("categoria modificada");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("categoria modificada"));
    }
    @Order(12)
    @Test
    void testUpdateNotFound() throws Exception{
        int id = 10;
        CategoriaRequest request = new CategoriaRequest("Categoria modificada");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se puede efectuar la modificación, " +
                        "la categoria no existe"));
    }
    @Order(13)
    @Test
    void testUpdateDuplicatedName() throws Exception {
        int id = createId;
        CategoriaRequest request = new CategoriaRequest("Categoria 2");
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/categorias/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "No se puede efectuar la modificación, " +
                                "el nombre de la categoria ya existe"));

    }
    @Order(14)
    @Test
    void testDelete() throws Exception{
        int id = createId;

        this.mockMvc.perform(delete("/api/categorias/"+id))
                .andExpect(status().isOk())
                .andExpect(content().string(String.format("Se ha eliminado " +
                        "correctamente la categoria con id: %d",createId)));
    }
    @Order(15)
    @Test
    void testDeleteNotFounc() throws Exception{
        int id = 10;
        this.mockMvc.perform(delete("/api/categorias/"+id))
                .andExpect(status().isNotFound())
                .andExpect(content().string(String.format("No existe una categoria con id: 10")));

    }
    @Order(16)
    @Test
    void testDeleteWithProductos() throws Exception{
        int id =1;

        this.mockMvc.perform(delete("/api/categorias/"+id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede eliminar la categoria, " +
                        "la categoria tiene productos asociados"));
    }


}
