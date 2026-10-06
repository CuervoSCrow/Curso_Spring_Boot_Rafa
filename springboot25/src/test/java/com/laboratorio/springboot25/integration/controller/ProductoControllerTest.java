package com.laboratorio.springboot25.integration.controller;

import com.laboratorio.springboot25.dto.ProductoResponse;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private static Integer createId;

    @Test
    @Order(1)
    void testFindProductoById()throws Exception {
        int id =1;

        this.mockMvc.perform(get("/api/productos/find")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(id))
                .andExpect(jsonPath("$.nombre").value("Producto 1"));
    }
    @Test
    @Order(2)
    void testFindProductoByNombre() throws Exception {
        String nombre="Producto 1";

        this.mockMvc.perform(get("/api/productos/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Producto 1"));
    }
    @Test
    @Order(3)
    void testFindProductoNotFound() throws Exception{
        String nombre = "Mouse";

        this.mockMvc.perform(get("/api/productos/find")
                        .param("nombre", nombre))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado el producto buscado"));
    }
    @Test
    @Order(4)
    void testFindProductoWithTwoParams() throws Exception{
        this.mockMvc.perform(get("/api/productos/find")
                        .param("id","1")
                        .param("nombre","Mouse"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Test
    @Order(5)
    void testFindProductosWithOutParams() throws Exception{
        this.mockMvc.perform(get("/api/productos/find"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Test
    @Order(6)
    void testFindAll() throws Exception{

        this.mockMvc.perform(get("/api/categorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }
    @Test
    @Order(7)
    void testFindByNombreContaining() throws Exception{
        String infix="Duct";

        this.mockMvc.perform(get("/api/productos/{infix}",infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(9));
    }


}
