package com.laboratorio.springboot25.unit.controller;

import com.jayway.jsonpath.JsonPath;
import com.laboratorio.springboot25.controller.ProductoController;
import com.laboratorio.springboot25.dto.CategoriaResponse;
import com.laboratorio.springboot25.dto.ProductoRequest;
import com.laboratorio.springboot25.dto.ProductoResponse;
import com.laboratorio.springboot25.exception.ResourceNotFoundException;
import com.laboratorio.springboot25.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import javax.swing.text.AbstractDocument;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = ProductoController.class)
public class ProductoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

//    ------------- Test Find Productos -------------------
    @Test
    void testFindProductoById() throws Exception {
        int id =1;
        ProductoResponse producto = new ProductoResponse(
                id,1,"Mouse",
                10.0, LocalDate.now() );
        when(productoService.findProductoById(id))
                .thenReturn(Optional.of(producto));

        this.mockMvc.perform(get("/api/productos/find")
                    .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(id))
                .andExpect(jsonPath("$.nombre").value("Mouse"));

    }
    @Test
    void testFindProductoByNombre() throws Exception {
        String nombre="Producto 1";
        ProductoResponse producto = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());
        when(productoService.findProductoByNombre(nombre))
                .thenReturn(Optional.of(producto));

        this.mockMvc.perform(get("/api/productos/find")
                        .param("nombre", nombre))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }
    @Test
    void testFindProductoNotFound() throws Exception{
        String nombre = "Mouse";
        when(this.productoService.findProductoByNombre(nombre))
                .thenReturn(Optional.empty());

        this.mockMvc.perform(get("/api/productos/find")
                        .param("nombre", nombre))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No se ha encontrado el producto buscado"));
    }
    @Test
    void testFindProductoWithTwoParams() throws Exception{
        this.mockMvc.perform(get("/api/productos/find")
                    .param("id","1")
                    .param("nombre","Mouse"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La busqueda debe tener un parametro"));
    }
    @Test
    void testFindAll()throws Exception{
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(1,1,"Mouse",10.0,LocalDate.now()),
                new ProductoResponse(2,1,"Teclado",10.0,LocalDate.now()),
                new ProductoResponse(3,1,"Monitor",10.0,LocalDate.now())
        );
        when(this.productoService.findAllOrderByNombreAsc()).thenReturn(productos);

        this.mockMvc.perform(get("/api/productos/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void testFindAllNoContent() throws Exception{
        when(this.productoService.findAllOrderByNombreAsc()).thenReturn(List.of());
        this.mockMvc.perform(get("/api/productos/findAll"))
                .andExpect(status().isNoContent());
    }
    @Test
    void testFindByNombreContaining() throws Exception{
        String infix="TegO";
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(3, 1,
                        "Disco externo", 80.0, LocalDate.now()));
        when(this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(productos);
        this.mockMvc.perform(get("/api/productos/{infix}",infix))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void testFindByNombreContainingNotContent()throws Exception{
        String infix="TegO";
        when(this.productoService.findByNombreContainingIgnoreCaseOrderByNombreAsc(infix))
                .thenReturn(List.of());
        this.mockMvc.perform(get("/api/productos/{infix}",infix))
                .andExpect(status().isNoContent());
    }
    @Test
    void testFindByCategoria() throws  Exception{
        int id = 1;
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(1,1,"Mouse",
                        10.0,LocalDate.now()),
                new ProductoResponse(2,1,"Teclado",
                        10.0,LocalDate.now()),
                new ProductoResponse(3,1,"Disco externo",
                        80.0,LocalDate.now()));
        when(this.productoService.findCategoriaIdOrderByNombreAsc(id))
                .thenReturn(productos);
        this.mockMvc.perform(get("/api/productos/categoria/{id}",id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }
    @Test
    void testFindCategoriaNotFOund() throws  Exception{
        int id = 1;
        when(this.productoService.findCategoriaIdOrderByNombreAsc(id))
                .thenReturn(List.of());
        this.mockMvc.perform(get("/api/productos/categoria/{id}",id))
                .andExpect(status().isNoContent());
    }
    @Test
    void testCreate() throws Exception{
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        ProductoResponse producto = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now()
        );

        when(this.productoService.createProducto(any(ProductoRequest.class)))
                .thenReturn(producto);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Mouse"));

    }
    @Test
    void testCreateInexistingProducto() throws Exception{
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        when(this.productoService.createProducto(any(ProductoRequest.class)))
                .thenThrow(new ResourceNotFoundException("No existe la categoria indicada, " +
                        "no se puede crear el producto"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No existe la categoria indicada, " +
                        "no se puede crear el producto"));
    }
    @Test
    void testUpdate() throws Exception{
        int id=1;
        ProductoRequest request = new ProductoRequest(
                1,"Mouse",10.0);
        ProductoResponse producto = new ProductoResponse(
                1,1,"Mouse",10.0,LocalDate.now());
        when(this.productoService.updateProducto(anyInt(),any(ProductoRequest.class)))
                .thenReturn(producto);
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(put("/api/productos/"+id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value(1))
                .andExpect(jsonPath("$.nombre").value("Mouse"));


    }

}
